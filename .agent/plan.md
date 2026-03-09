# Project Plan

Build a gamified To-Do List app in Java with an 'Avatar & Skin' system. Key components: Skin.java model, activity_wardrobe.xml with FrameLayout and horizontal RecyclerView, SkinAdapter.java with rarity-based colors and purchase logic, and SharedPreferences for gold/skins. Use placeholders for assets.

## Project Brief

# Project Brief: Legend-Tasker (Java Edition)

A gamified To-Do List application built with Java and the Android View System. Inspired by Mobile Legends, this app rewards productivity with a "Skin & Avatar" system, allowing users to earn gold through tasks to unlock and equip legendary visual upgrades.

### Features
1. **Quest Reward System**: A task manager where users earn Gold based on task difficulty: Easy (10g), Medium (25g), and Hard (50g).
2. **Layered Avatar Wardrobe**: A dynamic visual system using `FrameLayout` that allows a base avatar to be overlaid with various unlocked skins.
3. **Rarity-Based Skin Shop**: A `RecyclerView` marketplace featuring skins with distinct rarities—Common, Elite (Blue), Epic (Purple), and Legend (Gold)—each with unique price points.
4. **Economic Persistence**: A robust data layer using `SharedPreferences` to track user Gold balance and a collection of unlocked skins.

### High-Level Technical Stack
* **Language**: Java
* **UI Framework**: Android View System (XML Layouts)
* **UI Components**: `RecyclerView`, `CardView`, and `FrameLayout` for complex UI layering.
* **Persistence**: `SharedPreferences` (for economy and skin ownership tracking).
* **Architecture**: Model-View-Adapter (MVA) pattern for list management and state handling.

## Implementation Steps

### Task_1_Core_Models_And_Data: Define Java models for Task and Skin, and implement a SharedPreferences manager for gold and skin persistence.
- **Status:** IN_PROGRESS
- **Acceptance Criteria:**
  - Task and Skin classes created in Java
  - SharedPreferences manager handles gold, unlocked skins, and equipped skin
  - Project builds successfully
- **StartTime:** 2026-03-08 21:57:24 IST

### Task_2_Quest_System_UI: Implement the Quest System in MainActivity using a RecyclerView to list tasks with difficulty-based gold rewards.
- **Status:** PENDING
- **Acceptance Criteria:**
  - RecyclerView displays tasks
  - Task creation dialog with Easy/Medium/Hard difficulty
  - Gold balance updates in UI when tasks are completed
  - Java and XML used for implementation

### Task_3_Wardrobe_And_Shop_UI: Implement the WardrobeActivity with a FrameLayout for the avatar and a RecyclerView for the Skin Shop.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Layered avatar system using FrameLayout and placeholders
  - Skin Shop RecyclerView with rarity colors (Elite-Blue, Epic-Purple, Legend-Gold)
  - Purchase and Equip logic working
  - Navigation between Quest and Wardrobe screens

### Task_4_Final_Polish_And_Verification: Apply Material 3 styling, vibrant colors, Edge-to-Edge display, and create an adaptive app icon. Perform final verification.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Material 3 theme with vibrant colors applied
  - Edge-to-Edge display implemented
  - Adaptive app icon matching the theme
  - App builds and runs without crashes
  - Existing tests pass

