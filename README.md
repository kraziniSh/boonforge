# boonforge

Curse CurseForge and their covalent bloatware!

**boonforge** is a CLI app for downloading CurseForge Minecraft modpacks.

## Abandonment

I decided to drop boonforge.

It was built from the ground up in a private repository for a short time, continuing the goal
of an unfinished Java app.

After more thinking about the usage scenarios of this tool, I deem it better to instead make a
website dedicated to it.

I'll leave this under the MIT license (was MPL 2.0 before abandoning it), in case anyone wants to
build from it, or even study it **(and judge me!)**.

## The New API's Curse

[Overwolf | CurseForge are progressively rolling out their new API.](https://mailchi.mp/overwolf/whats-new-with-overwolf-curseforge-november3)

What this means for **boonforge**:

- development will continue until they destroy the old (current) API
- we will request CurseForge for an API key and, depending on the outcome, boonforge development
  will continue...
- ...along with a ~~bloat~~ware version 👀

## Installing

- Download the latest boonforge distribution
- Open a terminal in the `bin` directory
- **IF YOU DON'T HAVE JAVA ENVIRONMENT VARIABLES SET UP:**
- `javapath`; automatically sets up environment variable for Minecraft's bundled JRE. **Not
  implemented yet.**
- `boonforge [ARGS] --command [OPTIONS]`

## Usage

Download files in a new folder in the current directory (with the URL)

    boonforge download "https://www.curseforge.com/minecraft/modpacks/a-modpack-with-a-very-long-name-for-complaining-about-long-links-at-CurseForge"

Download files in a new folder in the current directory (with the archive)

    boonforge download "someDirectory\modpack.zip"

Download and install in `.minecraft`

    boonforge install "https://www.curseforge.com/minecraft/modpacks/finally-something-readable"

## Linking

### DYNAMICALLY in GRADLE

[//]: # (TODO DYNAMICALLY in GRADLE)

### STATICALLY

[//]: # (TODO STATICALLY)

## License

~~Mozilla Public License 2.0~~
[MIT License](LICENSE.txt)
