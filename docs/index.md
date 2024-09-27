## Home
### Introduction
Hi i'm drone 2703 and in my free time i love to program in java. And convenient enough, Minecraft `Java` editon is build on it!
This documentation contains all useful information about the HexCore project, it's usages and commands
### Vision
This project is made to make a stable platform to include the drone aspect to Minecraft, it will also serve as an 
platform to build new projects in the future, keeping its simplicity and maintaining the hive style!

## Stats for the programming drone
### What library's did it use?
- `Spigot API` version: `1.21.1-R0.1-SNAPSHOT` [Check out](https://www.spigotmc.org/wiki/buildtools/)
- `Hibernate` version: `6.1.5.Final` [Check out](https://hibernate.org/)
- `HikariCP` version: `5.0.1` [Check out](https://github.com/brettwooldridge/HikariCP)
- `ACF Paper` version `0.5.1-SNAPSHOT` [Check out](https://github.com/aikar/commands)
- `Boosted Yaml` version `1.3.5` [Check out](https://www.spigotmc.org/threads/%E2%9A%A1boostedyaml-feature-rich-standalone-library-updater-comments-yaml-1-2-compliant-%E2%9A%A1.545585/)

## Commands
### Hive
 Register yourself into the minecraft hive to be able to enjoy all the programming ~
``` java
/hive register [droneID]
``` 
##### Displays a help menu into the game
``` java
/hive help
```

### Drone
#### Toggling various settings for targeted drone, or itself
##### Toggles idprepend setting, this requires the drone to use it's id before every massage, like so: `2703 :: beep`
``` java
/drone settings toggle idprepend [droneID/Player]
``` 
##### Toggles corruption setting, this replaces the drone messages with some glitchy text generated in the iconic ZalgoText format
``` java
/drone settings toggle corruption [droneID/Player]
```
##### Toggles the battery status, This will allow a drone to have a displayed battery with every message.

``` java
/drone settings toggle battery [droneID/Player]
```
##### Releases a drone from all its settings, this can be used at any time and the drone itself is able to control it

``` java
/drone emergencyrelease [droneID/Player]
```

### Consent Center
#### Consenting drones and associates to eachother to give them `full` control over each-other

!!! danger

    Only accept people you really trust and feel comfortable with to be controlled. It's something to take serious to avoid incidents!
##### Send an invitation to targeted drone / associate 
``` java
/hive consent invite [droneID/Player]
``` 
##### Accepts a pending invite
``` java
/hive consent accept [droneID/Player]
``` 
##### Removes a targeted drone / associate from it's consent list
``` java
/hive consent remove [droneID/Player]
``` 
##### Releases a drone from all its settings, this can be used at any time and the drone itself is able to control it
