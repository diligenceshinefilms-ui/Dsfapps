# UI Structure & Navigation Architecture - Diligence Shine Films

**Application:** Diligence Shine Films  
**Tagline:** Turn Your Vision Into Cinema.  
**Package:** `com.dsf.apps`  
**Current Phase:** Phase 2 (Premium Application Shell)  

---

## 1. Top-Level Flow

```
[App Launch]
     │
     ▼
[SplashScreen] (Branded animated gold aperture & typography)
     │
     ▼
[OnboardingScreen] (3-slide interactive feature overview)
  - Slide 1: Character Identity Lock (100% facial consistency)
  - Slide 2: Cinematic Camera Matrix (Lenses & dynamic framing)
  - Slide 3: Emotive Multi-Lingual Speech (Hindi, Marathi, English)
     │
     ▼
[MainShellScreen] (Scaffold with Obsidian Glassmorphic Bottom Bar)
  ├── Tab 1: HOME (Dashboard, Quick Create, Recent, Presets)
  ├── Tab 2: CREATE (10-Step Directed Cinema Workflow)
  ├── Tab 3: PROJECTS (Drafts, Completed Masters, Favorites)
  ├── Tab 4: TEMPLATES (Hollywood, Bollywood, Noir, Sci-Fi)
  └── Tab 5: PROFILE (Account, Subscriptions, Voice Models)
```

---

## 2. Navigation Architecture Details

### 2.1 Destinations
- **`Screen.Splash` (`splash`):** Startup animated entrance. Automatically navigates to onboarding after entrance sequence.
- **`Screen.Onboarding` (`onboarding`):** HorizontalPager carousel with skip button and page indicators.
- **`Screen.MainShell` (`main_shell`):** Hosts the bottom navigation with tab switching.
  - Sub-destinations:
    - `MainDestination.HOME` (`tab_home`)
    - `MainDestination.CREATE` (`tab_create`)
    - `MainDestination.PROJECTS` (`tab_projects`)
    - `MainDestination.TEMPLATES` (`tab_templates`)
    - `MainDestination.PROFILE` (`tab_profile`)

### 2.2 Inset and Gesture Handling
- Full-bleed edge-to-edge architecture via `enableEdgeToEdge()`.
- Bottom navigation respects `WindowInsets.navigationBars` with a dedicated safe padding container so gesture navigation pills or 3-button system bars never overlap icons or labels.
- Android system back gestures handled via `BackHandler`: navigating backwards on secondary tabs smoothly pops back to the `HOME` tab before exiting the application.

---

## 3. UI State Containers

- **Loading State:** `CinematicLoadingIndicator` with rotating gold aperture.
- **Empty State:** `CinematicEmptyState` with category icon, message, and call-to-action button.
- **Error State:** `CinematicErrorState` with retry affordance and error messaging.
- **Success State:** `CinematicBadge` / glassmorphic card confirmation.
