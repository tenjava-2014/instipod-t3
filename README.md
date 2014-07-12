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

Features
--------

- During a storm, lightning has a chance to hit nearby redstone wire and cause a (unwanted?) pulse.
- During a storm, there is a chance it can start raining "Cats and Dogs."
- Hitting a chicken might anger it enough to start bombing you with anvils.
- Running out of food might cause you to start vomiting dirt everywhere.
  * Restoring your food level above the set level will stop the effect
- An open API for more events!
  * Scroll down to see the developer documentation!

Plugin Usage
------------

After installing the plugin JAR in your server's plugins folder, no extra work is required.  The plugin will load default values in the configuration file, which should set you up with random events right away!
If you want to customize some of the event factors, they are provided in the config.yml file created on first server run.  Below is information on each of the configuration options:

```
debug: false
```
This is a boolean entry mostly for me (the plugin author) to show plugin debugging message.  You are free to enable this and view the extra messages too, however, it does generate quite a bit of output.

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


### Angry_Chicken Section

```
enabled: true
```
This is a boolean entry that controls whether or not the Angry Chicken Event is enabled.

```
chance: 20
```
This is an integer entry that controls the chance of the event occurring (out of 100).

```
event_length: 10
```
This is an integer entry that controls the length of the Angry Chicken Event (in seconds).

```
bomb_height: 15
```
This is an integer entry that controls the height above the player in which the chicken spawns Anvils to fall (in blocks).


### Vomit Section

```
enabled: true
```
This is a boolean entry that controls whether or not the Vomit Event is enabled.

```
chance: 5
```
This is an integer entry that controls the chance of the event occurring (out of 100).

```
event_length: 10
```
This is an integer entry that controls the length of the Vomit Event (in seconds).

```
hunger_level: 3
```
This is an integer entry that controls the hunger level at which the event can occur (Full Hunger is equal to 20);

```
effect_strength: 2
```
This is an integer entry that controls the strength of the Weakness and Hunger effects placed on the player when vomiting.


Developer Documentation
-----------------------

Using the open API in InstiEvents, you can add custom events and create listeners to monitor when events occur.

### Creating a listener

```
import com.tenjava.entries.instipod.t3.api.InstiEventListener;
import com.tenjava.entries.instipod.t3.api.CallableEvent;

public class ExampleListener implements InstiEventListener {

public void initEvents() {
//register your custom events (if any) in this method, as shown below
}

public void eventOccured(CallableEvent event, String name) {
//this method will be called when any event occurs, with event being the event class, and name being the name of the event.
}

}
```

Above is an example listener for events.  Please note, both methods are required even if one or more methods are blank.
After you have a Listener made, you must register the listener with InstiEvents like shown below:

```
EventRegistrar.getInstance().registerListener(new ExampleListener());
```
(Don't forget to import com.tenjava.entries.instipod.t3.EventRegistrar!)

Placing that register line in your plugin onEnable() method is the recommended place.


### Creating Custom Events

To create a custom event, create a new class implementing:
- CallablePlayerEntityInteractEvent for an entity event to execute on Entity Damage by Player.
- CallablePlayerEvent for a storm event to randomly execute during a storm.
- CallablePlayerHungerEvent for a hunger event to randomly occur when hunger changes.

A basic hunger event looks like this:
```
import com.tenjava.entries.instipod.t3.api.CallablePlayerHungerEvent;
import org.bukkit.entity.Player;

public class ExampleEvent implements CallablePlayerHungerEvent {
    
    @Override
    public void call(Player p, int hunger) throws Exception {
        //this method is called when the event should occur
        //player p is the player object who this event is occuring to
        //int hunger is the player's hunger level at the time of the event starting
    }

    @Override
    public String getEventName() {
        //this method should always only return the name of the event
        return "ExampleEvent";
    }

}
```

A basic storm event looks like this:
```
import com.tenjava.entries.instipod.t3.api.CallablePlayerEvent;
import org.bukkit.entity.Player;

public class ExampleEvent implements CallablePlayerEvent {
    
    @Override
    public void call(Player p) throws Exception {
        //this method is called when the event should occur
        //player p is the player object who this event is occuring to
    }

    @Override
    public String getEventName() {
        //this method should always only return the name of the event
        return "ExampleEvent";
    }

}
```

And, a basic entity event looks like this:
```
import com.tenjava.entries.instipod.t3.api.CallablePlayerEntityInteractEvent;
import org.bukkit.entity.Player;

public class ExampleEvent implements CallablePlayerEntityInteractEvent {
    
    @Override
    public void call(Player p, Entity e) throws Exception {
        //this method is called when the event should occur
        //player p is the player object who this event is occuring to
        //entity e is the entity object who the player damaged
    }

    @Override
    public String getEventName() {
        //this method should always only return the name of the event
        return "ExampleEvent";
    }

}
```

After you have the event class created, you now need to register the event with a register line inside your initEvents() method in an InstiEventsListener, like so:
```
EventRegistrar.getInstance().registerStormEvent(new ExampleEvent(), 100); //For a storm event: First arugment is the event class, and the second is the percent chance of it occuring
EventRegistrar.getInstance().registerHungerEvent(new ExampleEvent(), 100); //For a hunger event: First arugment is the event class, and the second is the percent chance of it occuring
EventRegistrar.getInstance().registerEntityEvent(new ExampleEvent(), 100); //For an entity event: First arugment is the event class, and the second is the percent chance of it occuring
```

That's it!