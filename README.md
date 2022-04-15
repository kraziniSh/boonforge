# boonforge
Curse CurseForge and their covalent bloatware!

**boonforge** is a CLI app for downloading CurseForge Minecraft modpacks.

# The New API’s Curse
[Overwolf | CurseForge are progressively rolling out their new API.](https://mailchi.mp/overwolf/whats-new-with-overwolf-curseforge-november3)

What this means for **boonforge**:
- development will continue until the old (current) API is destroyed
- we will request CurseForge for an API key and, depending on the outcome, boonforge development will continue...
- ...along with a ~bloat~ware version 👀

# Installing
### IF YOU ONLY HAVE MINECRAFT INSTALLED (no JRE/JDK)
[//]: # (TODO Bundled Java instructions)

# Usage
Download modfiles in a new folder in current directory (with URL)

    boonforge download "https://www.curseforge.com/minecraft/modpacks/"

Download modfiles in a new folder in current directory (with archive)

    boonforge download "randomfolder\\modpack.zip"

Download and install in .minecraft

    boonforge install "https://www.curseforge.com/minecraft/modpacks/"
