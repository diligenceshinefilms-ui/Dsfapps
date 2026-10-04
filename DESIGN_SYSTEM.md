# Design System Specification - Diligence Shine Films

**Application:** Diligence Shine Films  
**Tagline:** Turn Your Vision Into Cinema.  
**Package:** `com.dsf.apps`  
**Foundation Phase:** Phase 1  

---

## 1. Design Philosophy: Cinematic Prestige

Diligence Shine Films delivers an immersive, director-grade cinematic visual language. Moving far beyond generic AI defaults, the interface uses an **Obsidian Dark** canvas with **Radiant Cinema Gold (`#F5C518`)**, **Electric Cyan lens flares (`#38BDF8`)**, and glassmorphic elevated containers.

- **Dark-First:** Defaulting to Obsidian Black (`#0A0A0C`) with subtle film grain/vignette atmosphere.
- **Precision:** 8.dp structural spacing grid, 48.dp minimum interactive touch targets.
- **Responsive & Safe:** Full-bleed edge-to-edge support with explicit `WindowInsets.statusBars` and `WindowInsets.navigationBars` handling.

---

## 2. Color Palette & Tokens

### 2.1 Primary & Accent Palette
- **Cinema Gold (`#F5C518`):** Main branding, primary action buttons, keyframe highlights.
- **Cinema Amber (`#E5A910`):** Secondary gradient end-stop, active states.
- **Burnished Gold (`#B45309`):** Container borders and pressed ripple highlights.
- **Electric Cyan (`#38BDF8`):** Anamorphic lens flares, technical camera badges, timeline markers.
- **Recording Red (`#EF4444`):** Recording indicators, error states, and destructive actions.
- **Master Green (`#10B981`):** Render completion and verified facial lock status.

### 2.2 Surface & Elevation Tokens (Dark Mode)
- **`ObsidianBlack` (`#0A0A0C`):** Root background canvas.
- **`ObsidianSurface` (`#131317`):** Primary screen surfaces and lists.
- **`ObsidianCard` (`#1B1B22`):** Elevated glassmorphic cards and input sections.
- **`ObsidianElevated` (`#242430`):** Dropdown menus, bottom sheets, and floating dialogs.
- **`ObsidianBorder` (`#2E2E3E`):** Hairline container borders.

---

## 3. Typography Hierarchy

| Style Token | Weight | Size (sp) | Tracking (sp) | Purpose |
| :--- | :--- | :--- | :--- | :--- |
| **`displayLarge`** | Black (900) | 36.sp | -0.5.sp | High-impact film titles and hero banners |
| **`displayMedium`**| ExtraBold (800) | 28.sp | 0.sp | Main screen titles |
| **`headlineLarge`** | Bold (700) | 22.sp | 0.15.sp | Section titles and wizard step headers |
| **`headlineMedium`**| SemiBold (600) | 18.sp | 0.15.sp | Card titles and preview labels |
| **`titleMedium`** | Medium (500) | 15.sp | 0.15.sp | Secondary options and camera setting titles |
| **`bodyLarge`** | Normal (400) | 16.sp | 0.3.sp | User prompts and long descriptions |
| **`bodyMedium`** | Normal (400) | 14.sp | 0.25.sp | Dialogue editor and help callouts |
| **`labelLarge`** | Bold (700) | 14.sp | 0.5.sp | Primary button text and key CTAs |
| **`labelMedium`** | SemiBold (600) | 12.sp | 0.5.sp | Badges (e.g., "16:9", "FREE 30s", "4K") |
| **`labelSmall`** | Medium (500) | 10.sp | 0.5.sp | Timecodes (e.g., `00:14.28`) |

---

## 4. Spacing, Shapes & Motion

- **Spacing:** `none` (0.dp), `xxs` (2.dp), `xs` (4.dp), `s` (8.dp), `m` (12.dp), `l` (16.dp), `xl` (20.dp), `xxl` (24.dp), `xxxl` (32.dp), `huge` (40.dp), `massive` (48.dp).
- **Shapes:**
  - Extra Small: `4.dp` (Badges, tags)
  - Small: `8.dp` (Small controls)
  - Medium: `12.dp` (Video frames, timeline clips)
  - Large: `16.dp` (Cinema cards, bottom sheets)
  - Pill: `999.dp` (Action buttons, floating controls)
- **Motion:**
  - Spring with low stiffness for cinematic dialog entry.
  - Shimmer pulse for AI generation queues.

---

## 5. Accessibility & Google Play Policy

- Minimum touch targets strictly >= 48.dp.
- TalkBack content descriptions on all icons and media previews.
- Edge-to-edge support with `enableEdgeToEdge()` and insets padding.
- Android 16 system color scheme support (Dark / Light / Dynamic).
