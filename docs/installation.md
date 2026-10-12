# Installation

[Documentation index](README.md)

United Minecraft runs on either of Minecraft's two main mod loaders, **Fabric**
or **NeoForge**. Pick whichever you already use; if you have no preference,
Fabric is the better default, for a reason worth knowing up front:

> Fabric builds are released the day a new Minecraft version comes out.
> NeoForge builds follow once NeoForge itself supports that version, which
> usually takes days and can take weeks. Both get the same features - the
> NeoForge build is simply sometimes behind on the newest Minecraft version.

Each release provides a separate jar per loader, named so you can tell them
apart: `united-minecraft-<version>+mc<minecraft version>-fabric.jar` ends in `-fabric`, and
`united-minecraft-<version>+mc<minecraft version>-neoforge.jar` ends in `-neoforge`. The `mc<minecraft version>`
part is the Minecraft version that jar is for.

Either way, United Minecraft is a client-side mod. Nothing needs to be
installed on the server you play on, and it works against ordinary vanilla
servers. The one exception is optional: Structure Voices (and the structures in
the Scanner's Biomes and Structures category) need to know where structures are, which only the server
does. They work in single player as-is; for multiplayer, put the same jar in
the server's `mods` folder too (Fabric servers also need Fabric API). Players
without the mod can still join that server, and it changes nothing else.

## Installing on Fabric

1. **Download United Minecraft.**
   From this repository's
   [Releases page](https://github.com/blindgoofball/united-Minecraft/releases),
   download the latest jar ending in `-fabric.jar`. Note the Minecraft version
   in its filename - you'll use that same version below.

2. **Install Fabric Loader.**
   Go to the [Fabric installer page](https://fabricmc.net/use/installer/) and
   download the installer for your operating system. Run it, set the Minecraft
   version to the one from step 1, and click Install. This adds a "Fabric
   Loader" profile to the official Minecraft Launcher - nothing else here needs
   changing.

3. **Download Fabric API.**
   Fabric needs this; NeoForge does not. Get the Fabric API release built for
   that same Minecraft version from
   [Modrinth](https://modrinth.com/mod/fabric-api) or
   [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api).

4. **Put both jars in your mods folder.**
   That's a folder called `mods` inside your Minecraft folder:
   - Windows: `%appdata%\.minecraft\mods`
   - macOS: `~/Library/Application Support/minecraft/mods`
   - Linux: `~/.minecraft/mods`

   If it doesn't exist yet, launch the Fabric profile once to have it created
   automatically, then close the game and drop both `.jar` files straight into
   it - not inside any subfolder.

5. **Launch the game.**
   Open the official Minecraft Launcher, choose the Fabric profile from the
   installations dropdown, and click Play.

The first time the mod runs it turns the narrator on for you - see [Turning on the narrator](#turning-on-the-narrator).

## Installing on NeoForge

1. **Download United Minecraft.**
   From this repository's
   [Releases page](https://github.com/blindgoofball/united-Minecraft/releases),
   download the latest jar ending in `-neoforge.jar`. Note the Minecraft version
   in its filename - you'll use that same version below. If the newest release
   has no `-neoforge` jar, NeoForge hasn't caught up to that Minecraft version
   yet; use the most recent release that does have one.

2. **Make sure you have Java installed.**
   NeoForge's installer is a `.jar` file, and running one needs Java on your
   computer. Minecraft's launcher bundles its own Java for playing the game, but
   that copy isn't available for running an installer - so this is a separate
   thing you may need even if Minecraft already works fine.

   To check, open a terminal (Command Prompt or PowerShell on Windows, Terminal
   on macOS or Linux) and run:

   ```
   java -version
   ```

   If it reports a version number, you're set. If it says the command isn't
   found, install Java 25 from [Adoptium](https://adoptium.net/) - that's the
   version Minecraft itself runs on, so it will certainly run the installer.

   Fabric offers a `.exe` installer on Windows that skips this step entirely,
   which is one reason Fabric is the simpler starting point.

3. **Install NeoForge.**
   Go to the [NeoForge downloads page](https://neoforged.net/) and get the
   installer for the Minecraft version from step 1. Then run it from a terminal,
   in the folder you downloaded it to:

   ```
   java -jar neoforge-<version>-installer.jar
   ```

   Running it this way is more reliable than double-clicking, since `.jar` files
   aren't always associated with Java. Choose "Install client" and let it
   finish. This adds a NeoForge profile to the official Minecraft Launcher.

4. **Put the jar in your mods folder.**
   That's a folder called `mods` inside your Minecraft folder:
   - Windows: `%appdata%\.minecraft\mods`
   - macOS: `~/Library/Application Support/minecraft/mods`
   - Linux: `~/.minecraft/mods`

   If it doesn't exist yet, launch the NeoForge profile once to have it created
   automatically, then close the game and drop the `.jar` straight into it - not
   inside any subfolder. There's no second jar to install on NeoForge; nothing
   corresponds to Fabric API here.

5. **Launch the game.**
   Open the official Minecraft Launcher, choose the NeoForge profile from the
   installations dropdown, and click Play.

The first time the mod runs it turns the narrator on for you - see [Turning on the narrator](#turning-on-the-narrator).

## Turning on the narrator

The same on both loaders. United Minecraft narrates through Minecraft's
narrator setting rather than replacing it, so the mod stays silent while that
setting is off - and Minecraft ships with it off. To save you having to find it,
the mod switches the narrator to All the very first time it runs, before the
main menu. That happens once: if you turn the narrator off afterwards it stays
off.

To change it yourself at any time, press Ctrl+B in-game to toggle the narrator,
or go through Options > Accessibility Settings > Narrator.

If a screen reader (or other Prism-supported speech backend) is available,
United Minecraft speaks through it automatically for better screen-reader
output - there's nothing to set up for that part. Otherwise, it falls back to
Minecraft's normal narrator on its own.
