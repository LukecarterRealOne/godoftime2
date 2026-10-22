# God of Time — Forge 1.20.1 Mod

Become the God of Time. Three stances, 50 abilities, and a death that isn't really death.

## Setup
1. Install the Forge 1.20.1 MDK locally, or just drop this `src/` into a fresh Forge MDK for `47.2.0`.
2. Run `gradle wrapper` once (needs internet) to generate `gradlew` / `gradlew.bat` / `gradle-wrapper.jar` — only the `gradle-wrapper.properties` is included here.
3. `./gradlew build` → jar lands in `build/libs/`. `./gradlew runClient` to test.
4. `.github/workflows/build.yml` is set up to build the mod automatically on GitHub Actions (this is the `build.yml` you asked for).

## Controls
- **M** — cycle Mode: Yellow → Green → Blue → Yellow
- **[ / ]** — cycle the selected ability within your current mode
- **R** — use the currently selected ability

## Modes (50 abilities total, ~17/17/16 per mode)
- **Yellow** — offense: Astral Slash, Chrono Execute, Blade Storm, Erase (delete block/entity), Wrath of Hyperverse, etc.
- **Green** — support: Chrono Heal, Rewind Self, Bloom Burst, Second Chance, Temporal Shield, etc.
- **Blue** — time/space control: Time Freeze, Blink, Weather Control, Time Anchor, Eternal Moment, etc.

Full list with descriptions: `TimeAbility.java`.

## Death & Ascension
When you die, the death is cancelled. A title flashes:

> **I AM THE GOD OF HYPERVERSE**

~2.5 seconds later you're revived at full health holding the **Astral Sword** — a sword that deals effectively infinite damage and can erase (right-click) any block or entity it's pointed at, while shedding yellow astral-light particles. For 15 seconds after revival (and whenever you cast **Blade Storm**), two orbiting yellow-and-black astral blades circle behind you and strike any hostile creature within range.

## Notes / things you'll likely want to tune
- `AstralSwordItem.INFINITE_DAMAGE` is a big float rather than literal infinity, to avoid NaN/overflow weirdness in vanilla damage math; `hurtEnemy` also force-kills survivors.
- Ability effects are implemented with vanilla potion effects / particles / world calls — some (e.g. `World Rewind`, `Meteor Mark`) are intentionally simple, not full mechanics systems; extend `AbilityManager` as you like.
- No custom entity/renderer was used for the blades — they're a particle + damage-tick aura (`BladeAuraTicker`) so there's no model/animation work required to get it working.
- Textures are minimal placeholders generated for you; swap `textures/item/astral_sword.png` and `chrono_core.png` for real art.
- This was authored without access to compile against the actual Forge jars (no network in this environment), so double check against the 1.20.1 Forge API if anything shifted versions — the method names/signatures used match MC 1.20.1 / Forge 47.x as of writing.
