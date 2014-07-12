instipod's ten.java submission
==============================

[![ten.java](https://cdn.mediacru.sh/hu4CJqRD7AiB.svg)](https://tenjava.com/)

This is a submission for the 2014 ten.java contest.

- __Name:__ InstiEvents
- __Theme:__ What random events can occur in Minecraft?
- __Time:__ Time 3 (7/12/2014 14:00 to 7/13/2014 00:00 UTC)
- __MC Version:__ 1.7.9 (latest Bukkit beta)
- __Stream URL:__ https://twitch.tv/instipod

---------------------------------------

Compilation
-----------

- Download & Install [Maven 3](http://maven.apache.org/download.html)
- Clone the repository: `git clone https://github.com/tenjava/instipod-t3`
- Compile and create the plugin package using Maven: `mvn`

Maven will download all required dependencies and build a ready-for-use plugin package!


---------------------------------------

Usage
-----

After installing the plugin JAR in your server's plugins folder, no extra work is required.  The plugin will load default values in the configuration file, which should set you up with random events right away!
If you want to customize some of the event factors, they are provided in the config.yml file created on first server run.  Below is information on each of the configuration options:

```
debug: false
```
This is a boolean entry mosting for me (the plugin author) to show plugin debugging message.  You are free to enable this and view the extra messages too, however, it does generate quite a bit of output.

```
log_events: true
```
This is a boolean entry that controls whether the plugin should log to the console whenever a random event occurs.


### Global Section

```
frequency: 600
```
This is an integer entry on how many ticks (There are 20 ticks per second) should occur between storm event chances. i.e. The default will calculate chances and call events if needed every 600 ticks.

```
start_delay: 200
```
This is an integer entry on how many ticks (There are 20 ticks per second) the server should wait after a storm starts to start calculating event chances.


### Lightning_Redstone Section

```
enabled: true
```
This is a boolean entry that controls whether or not the Lightning Redstone Event is enabled.

```
chance: 15
```
This is an integer entry that controls the chance of the event occurring (out of 100).

```
overload_chance: 6
```
This is an integer entry that controls the chance of a redstone overload (and explosion) occurring (out of 100).

```
overload_power: 2
```
This is an integer entry that controls the power of the redstone overload explosion (4 is equal to 1 block of TNT).


### Cats_And_Dogs Section

```
enabled: true
```
This is a boolean entry that controls whether or not the Raining Cats and Dogs Event is enabled.

```
chance: 10
```
This is an integer entry that controls the chance of the event occurring (out of 100).

```
event_length: 10
```
This is an integer entry that controls the length of the Raining Cats and Dogs Event (in seconds).

```
spawn_height: 45
```
This is an integer entry that controls the height above the player in which the animals are spawned (in blocks).