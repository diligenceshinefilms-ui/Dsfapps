# Dependency Specification & Matrix - Diligence Shine Films

**Application:** Diligence Shine Films  
**Package:** `com.dsf.apps`  
**Gradle Version Catalog:** `gradle/libs.versions.toml`  
**Build System:** Android Gradle Plugin 9.1.1 + Gradle Kotlin DSL  

---

## 1. Active Dependencies in `:app`

| Group & Artifact | Version / Reference | Classification | Purpose |
| :--- | :--- | :--- | :--- |
| `androidx.compose:compose-bom` | `2024.09.00` | UI Foundation | Jetpack Compose Bill of Materials |
| `androidx.compose.ui:ui` | Managed by BOM | UI Core | Compose layout and drawing engine |
| `androidx.compose.ui:ui-graphics` | Managed by BOM | UI Graphics | Rendering primitives, shaders, color spaces |
| `androidx.compose.ui:ui-tooling-preview`| Managed by BOM | Tooling | IDE and preview runtime support |
| `androidx.compose.material3:material3` | Managed by BOM | Design System | Material Design 3 components |
| `androidx.compose.material:material-icons-core` | Managed by BOM | Icons | Core vector icons |
| `androidx.compose.material:material-icons-extended` | Managed by BOM | Icons | Extended cinematic and media icons |
| `androidx.activity:activity-compose` | `1.10.1` | Integration | ComponentActivity Jetpack Compose binding |
| `androidx.core:core-ktx` | `1.18.0` | Core | Kotlin extensions for Android framework APIs |
| `androidx.lifecycle:lifecycle-runtime-ktx` | `2.8.7` | Lifecycle | Coroutine lifecycle scopes |
| `androidx.lifecycle:lifecycle-runtime-compose` | `2.8.7` | State | `collectAsStateWithLifecycle()` Compose state adapter |
| `androidx.lifecycle:lifecycle-viewmodel-compose` | `2.8.7` | State | ViewModel integration with Compose navigation |
| `org.jetbrains.kotlinx:kotlinx-coroutines-core` | `1.10.2` | Concurrency | Asynchronous flow and coroutines |
| `org.jetbrains.kotlinx:kotlinx-coroutines-android`| `1.10.2` | Concurrency | Android Main dispatcher support |
| `androidx.room:room-runtime` | `2.7.0` | Database | SQLite object-relational mapping |
| `androidx.room:room-ktx` | `2.7.0` | Database | Room coroutine and Flow integration |
| `androidx.room:room-compiler` (KSP) | `2.7.0` | Annotation Proc | Room DAO and database code generation |
| `com.squareup.retrofit2:retrofit` | `2.12.0` | Networking | REST HTTP client for AI services |
| `com.squareup.retrofit2:converter-moshi`| `2.12.0` | Serialization | JSON to Kotlin entity mapping |
| `com.squareup.moshi:moshi-kotlin` | `1.15.2` | Serialization | Reflectionless JSON serialization |
| `com.squareup.moshi:moshi-kotlin-codegen` (KSP)| `1.15.2` | Annotation Proc | Moshi code generation |
| `com.squareup.okhttp3:okhttp` | `4.10.0` | Networking | HTTP/2 and TLS connection client |
| `com.squareup.okhttp3:logging-interceptor`| `4.10.0` | Diagnostic | HTTP traffic logging for debug mode |
| `com.google.firebase:firebase-bom` | `34.17.0` | Cloud Platform | Firebase SDK version coordination |
| `com.google.firebase:firebase-ai` | Managed by BOM | AI Engine | Firebase AI Logic & Gemini integration |
| `com.google.firebase:firebase-appcheck-recaptcha` | Managed by BOM | Security | App Check bot and abuse protection |
| `com.google.firebase:firebase-appcheck-debug` | Managed by BOM | Security | Debug App Check provider |

---

## 2. Cataloged Dependencies (Ready for Phased Activation)

| Library Key | Maven Coordinates | Planned Phase | Target Capability |
| :--- | :--- | :--- | :--- |
| `libs.coil.compose` | `io.coil-kt:coil-compose:2.7.0` | Phase 1 / Phase 5 | Async image loading for reference images and thumbnails |
| `libs.androidx.navigation.compose` | `androidx.navigation:navigation-compose:2.8.9` | Phase 2 | Type-safe navigation across screens |
| `libs.androidx.datastore.preferences` | `androidx.datastore:datastore-preferences:1.1.7` | Phase 2 / Phase 17 | User settings, theme preference, creator mode |
| `libs.firebase.auth` | `com.google.firebase:firebase-auth` | Phase 18 | User account authentication |
| `libs.androidx.credentials` | `androidx.credentials:credentials:1.5.0` | Phase 18 | Jetpack Credential Manager for Google Sign-In |
| `libs.androidx.credentials.play.services` | `androidx.credentials:credentials-play-services-auth:1.5.0` | Phase 18 | Google Play Services auth bridge |
| `libs.googleid` | `com.google.android.libraries.identity.googleid:googleid:1.1.1` | Phase 18 | Modern Google ID credential option |
| `libs.firebase.firestore` | `com.google.firebase:firebase-firestore` | Phase 19 | Cloud project and metadata sync |

---

## 3. Media & Billing Dependencies to Add in Later Phases

| Dependency / Capability | Target Artifact | Target Phase | Justification |
| :--- | :--- | :--- | :--- |
| **Media3 ExoPlayer & Transformer** | `androidx.media3:media3-exoplayer` & `media3-transformer` | Phase 12 & Phase 14 | Seamless multi-segment playback, concatenation, and timeline video editing |
| **Google Play Billing** | `com.android.billingclient:billing-ktx:7.1.1` | Phase 20 | In-app subscriptions (Creator, Pro, Studio plans) |
| **WorkManager** | `androidx.work:work-runtime-ktx:2.10.0` | Phase 9 / Phase 14 | Reliable background video rendering and segment polling |

---

## 4. Testing Dependencies

| Group & Artifact | Version | Scope | Purpose |
| :--- | :--- | :--- | :--- |
| `junit:junit` | `4.13.2` | `testImplementation` | Standard JVM unit testing |
| `org.robolectric:robolectric` | `4.16.1` | `testImplementation` | Android framework emulation on JVM (SDK 36) |
| `org.jetbrains.kotlinx:kotlinx-coroutines-test` | `1.10.2` | `testImplementation` | Test dispatchers and virtual time |
| `androidx.test:core` | `1.6.1` | `testImplementation` | AndroidX test core runners |
| `androidx.test.ext:junit` | `1.3.0` | `testImplementation` | JUnit 4 Android extensions |
| `androidx.compose.ui:ui-test-junit4` | Managed by BOM | `testImplementation` | Compose UI hierarchy testing |

---

## 5. Dependency Audit Conclusion

All active and cataloged dependencies are verified compatible with Kotlin 2.2.10, AGP 9.1.1, and Android 16 (API 36). No deprecated or conflicting libraries are present.
