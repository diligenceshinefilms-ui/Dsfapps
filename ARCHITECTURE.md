# Architecture Specification - Diligence Shine Films

**Application:** Diligence Shine Films  
**Tagline:** Turn Your Vision Into Cinema.  
**Package:** `com.dsf.apps`  
**Architecture Pattern:** Clean Architecture + MVVM + Unidirectional Data Flow (UDF)  
**UI Engine:** Jetpack Compose (Material 3)  
**Target Platform:** Android 16 (API 36)  

---

## 1. High-Level Architecture Overview

Diligence Shine Films adopts a clean, modular multi-tier architecture adhering to Android modern app architecture guidelines. The system isolates business logic from UI representation and data sources, enabling seamless scalability across AI video generation providers, local storage, cloud backends, and in-app billing.

```
┌──────────────────────────────────────────────────────────┐
│                   PRESENTATION LAYER                     │
│  Jetpack Compose Screens • ViewModels • UI States • M3   │
│  (Home, Create, Projects, Editor, Voice, Templates, Pay) │
└────────────────────────────┬─────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────┐
│                      DOMAIN LAYER                        │
│   UseCases • Domain Models • Provider Interfaces • Auth  │
│   (VideoPipeline, PromptEngine, VoiceEngine, Continuity) │
└────────────────────────────┬─────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────┐
│                       DATA LAYER                         │
│  Repositories • Room DB • DataStore • Remote APIs • S3   │
│  (VideoRepo, ProjectRepo, VoiceRepo, BillingRepo, Auth)  │
└──────────────────────────────────────────────────────────┘
```

---

## 2. Layer Specifications

### 2.1 Presentation Layer (`com.example.ui` / `feature:*`)
- **Jetpack Compose UI:** 100% declarative UI built with Material 3 components and custom cinematic theming.
- **ViewModels:** Single source of truth for screen state using `StateFlow` and `asStateWithLifecycle()`.
- **State Management:** Strict Unidirectional Data Flow (UDF). Screens receive an immutable `UiState` data class and emit typed `UiEvent` intents.
- **Navigation:** Type-safe Compose Navigation routing across core destinations:
  - `Splash` / `Onboarding`
  - `Home` (Dashboard, Recent Projects, Templates, AI Quick Tools)
  - `Create` (10-step wizard: Image -> Prompt -> Dialogue -> Voice -> Camera -> Style -> Render)
  - `Editor` (Media3 multi-track timeline: Video, Audio, Subtitles, VFX)
  - `Projects` (Drafts, Completed, Favorites, Exports)
  - `Templates` (Cinematic presets, aspect ratio templates)
  - `Profile` & `Subscription` (Entitlements, usage meter, billing tiers)

### 2.2 Domain Layer (`com.example.domain`)
- **Independent of Android Framework:** Pure Kotlin models and interfaces.
- **Key Domain Entities:**
  - `CinematicProject`: Master project containing scenes, timeline, settings, and render status.
  - `GenerationRequest`: Encapsulates prompt, reference image metadata, dialogue, camera style, resolution, and aspect ratio.
  - `GenerationJob`: State machine tracking asynchronous video generation (`QUEUED`, `PROCESSING`, `SEGMENTING`, `COMPLETED`, `FAILED`).
  - `VoiceProfile`: Multi-lingual voice metadata (Hindi, Marathi, English) with timbre and emotion settings.
  - `SubscriptionPlan`: Free (max 30s) vs Creator (60s) vs Pro vs Studio entitlements.
- **Use Cases:**
  - `CreateCinematicVideoUseCase`
  - `EnhanceCinematicPromptUseCase`
  - `AssembleVideoSegmentsUseCase`
  - `SynchronizeLipSyncUseCase`
  - `ManageProjectUseCase`

### 2.3 Data Layer (`com.example.data`)
- **Repository Pattern:** Repositories mediate between local storage and remote AI endpoints.
- **Local Persistence:**
  - **Room Database (`AppDatabase`):** Caches projects, scenes, generation history, audio assets, and drafts.
  - **DataStore:** Stores user preferences (theme, preferred language, default aspect ratio, onboarding flag).
- **Network & Remote APIs:**
  - **Retrofit + OkHttp:** Secure client with TLS 1.3, token interceptors, and error handling.
  - **Asynchronous Polling / Webhook Handlers:** For long-running AI video rendering jobs.
  - **Provider Abstraction (`VideoGenerationProvider`):** Decoupled interface allowing flexible switching between generation backends without modifying business logic.

---

## 3. Video Generation & Assembly Pipeline

```
[User Input: Reference Image + Prompt + Dialogue + Camera Specs]
                             │
                             ▼
              [Prompt Enhancement Engine]
                             │
                             ▼
              [Duration Segmenter (0-30s / 60s+)]
                             │
               ┌─────────────┴─────────────┐
               ▼                           ▼
      [Video Clip Generator]     [TTS / Voice Generator]
               │                           │
               └─────────────┬─────────────┘
                             ▼
                    [Lip-Sync Engine]
                             │
                             ▼
               [Media3 Video Segment Assembler]
                             │
                             ▼
              [Subtitles + Soundtrack Overlay]
                             │
                             ▼
                 [Final Cinematic Master]
```

- **Segment Breakdown for Free Tier (0-30s):**
  - Native generation chunks (e.g. 8s, 10s) assembled with seamless frame continuity and audio cross-fading.
- **Character Consistency Guarantee:**
  - Seed propagation and keyframe feature extraction from reference images across consecutive clips.

---

## 4. Security & Data Protection Architecture

1. **Zero Hardcoded Secrets:** All API keys and tokens injected via `BuildConfig` and secured backend proxies.
2. **Encrypted Storage:** User tokens and sensitive credentials managed via Android Keystore and Jetpack Credential Manager.
3. **Safe Storage Access:** Zero legacy broad storage permissions; uses modern Android Photo Picker (`PickVisualMedia`).
4. **Content Moderation:** In-flight prompt and image safety checks prior to submission to remote AI generators.
5. **Report & Takedown:** Built-in reporting system for generated video inspection.

---

## 5. Architectural Quality Attributes

- **Modularity:** High cohesion, low coupling between features.
- **Testability:** Unit tests for domain use cases and repositories; Robolectric tests for Compose integration.
- **Performance:** Asynchronous coroutines for non-blocking UI; offloaded video processing using WorkManager and Media3.
- **Accessibility:** Minimum 48dp touch targets, semantic labels, TalkBack compatibility, and dynamic text scaling.
