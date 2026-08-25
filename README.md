<div align="center">

![Go Fish — Banner](https://cdn.modrinth.com/data/nGvPJWKu/images/25dd2c1d4806a675818d7677149e6d5452f33ec7.png)

</div>

<hr style="border: 0; border-top: 3px solid #0784A8;">

# Go Fish Rehooked

Go Fish Rehooked is a fishing expansion for Minecraft that turns fishing into a dimension-spanning progression system.
Discover biome-specific catches, weather and moon events, custom fishing rods, inventory-based lures, crates, recipes,
and new treasure tables.

<hr style="border: 0; border-top: 2px solid #29313D;">

## Features

- New fish for the Overworld, Nether, End, full moons, rain, thunderstorms, snow, and high elevations.
- Nether lava fishing with specialized rods and fireproof catches.
- Nine extended fishing rods with different durability, sounds, experience, auto-smelting, and lava-fishing properties.
- Three inventory-based fishing accessories: **Simple Lure**, **Golden Fish**, and **Soul Lure**.
- Twelve crates containing resources, food, equipment, rods, fish, and rare rewards.
- Custom food, cooking, crafting, and fish-to-resource recipes.
- The **Deepfry** fishing-rod enchantment.
- A `/fish` command for operators.
- A fishing-focused advancement progression.
- A **Go Fish** creative tab containing the mod's registered items.

<hr style="border: 0; border-top: 2px solid #0784A8;">

## Fishing Overview

Go Fish Rehooked keeps the normal fishing interaction: cast a rod, wait for a bite, and retrieve the hook as usual. The
fishing location and current conditions determine which catches can be selected.

<div align="center">

_Screenshot placeholder — Show a player fishing in an Overworld biome, with the fishing rod, hook, and a newly caught
custom fish visible._

</div>

### Overworld

The Overworld includes vanilla junk, treasure, and fish categories, plus Go Fish fish and crates.

| Condition               | Examples                                                    |
|-------------------------|-------------------------------------------------------------|
| Icy biomes              | Icicle Fish, Snowball Fish                                  |
| Swamp or Mangrove Swamp | Slimefish, Lilyfish                                         |
| Ocean                   | Seaweed Eel                                                 |
| Badlands                | Terrafish                                                   |
| Plains or Forest        | Carrot Carp, Oakfish                                        |
| Full moon at night      | Lunarfish, Galaxy Starfish, Starry Salmon, Nebula Swordfish |
| Rain                    | Rainy Bass                                                  |
| Thunderstorm            | Thundering Bass                                             |
| Snow                    | Blizzard Bass                                               |
| Y level 150 or higher   | Cloudy Crab                                                 |

<div align="center">

_Screenshot placeholder — Show a catch affected by a biome, weather, full-moon, or high-altitude condition._

</div>

### Nether

Lava is a valid liquid for Go Fish fishing. Only lava-proof rods can safely fish in lava. Nether catches are protected
from fire and lava until they are picked up.

Nether fish include **Smokey Salmon**, **Magma Cod**, **Bonefish**, **Obsidian Halibut**, **Basalt Bass**,
**Spikerfish**, **Gilded Blackstone Carp**, **Blackstone Trout**, and **Soul Salmon**.

### The End

The End has its own fishing table with catches such as **Endfish**, **Matrix Fish**, **Ender Eel**, **Chorus Cod**,
**Dragonfish**, **Omega Floater**, and **Portal Puffer**.

### Fishing Bonuses

Fishing bonuses are read from the player's inventory when the hook is created. The item does not need to be held or
worn, and each distinct bonus item type is counted once.

- **Lure** increases fishing speed, capped at 5.
- **Luck** is added to the hook's Luck of the Sea value.
- **Auto-smelting** replaces a catch with its normal smelting result when a recipe exists.
- **Experience** changes the base experience value of a catch.

<hr style="border: 0; border-top: 2px solid #29313D;">

## Fishing Rods and Lures

### Fishing Rods

| Rod                    | Durability | Special property                              |
|------------------------|-----------:|-----------------------------------------------|
| Blaze Rod              |        125 | Auto-smelts catches and is lava-proof         |
| Skeletal Rod           |         75 | Lava-proof and uses skeleton sounds           |
| Diamond Reinforced Rod |        300 | Lava-proof                                    |
| Eye of Fishing         |        250 | End-themed rod                                |
| Frosted Rod            |        150 | Frosted-themed rod                            |
| Slime Rod              |        150 | Uses slime sounds                             |
| Rod of Souls           |        250 | Lava-proof and adds 5 base fishing experience |
| Matrix Rod             |        200 | Matrix-themed rod                             |
| Celestial Rod          |        150 | Adds 1 Luck of the Sea at night               |

<div align="center">

_Screenshot placeholder — Show several extended rods together, highlighting their different designs and special
properties._

</div>

All extended rods can still receive vanilla fishing enchantments.

### Lures and Accessories

| Item        | Bonus                                    | How to obtain                                       |
|-------------|------------------------------------------|-----------------------------------------------------|
| Simple Lure | `+1` Lure                                | Crafted from a Feather and Iron Nuggets             |
| Golden Fish | `+1` Lure                                | Found through fishing loot, including Golden Crates |
| Soul Lure   | `+1` Luck of the Sea in Soul Sand Valley | Crafted from 2 Soul Salmon and a Simple Lure        |

The **Deepfry** enchantment is a rare, single-level fishing-rod enchantment that enables auto-smelting on vanilla and Go
Fish rods.

<hr style="border: 0; border-top: 2px solid #0784A8;">

## Crates

Crates are collectible fishing rewards that can be placed as blocks or opened directly as items. They stack to 8 and use
their own loot tables.

<div align="center">

_Screenshot placeholder — Show a selection of crates from different locations, such as a Wooden Crate, Golden Crate,
Astral Crate, or End Crate._

</div>

| Crate                   | Fishing location                             |
|-------------------------|----------------------------------------------|
| Wooden Crate            | General Overworld fishing                    |
| Iron Crate              | General Overworld fishing                    |
| Golden Crate            | General Overworld and Nether fishing         |
| Diamond Crate           | General Overworld fishing                    |
| Supply Crate            | Plains, forests, and rivers in the Overworld |
| Frosted Crate           | Icy biomes                                   |
| Slimey Crate            | Swamp and Mangrove Swamp biomes              |
| Astral Crate            | Fishing under a full moon in the Overworld   |
| Fiery Crate             | Nether fishing                               |
| Soul Crate              | Soul Sand Valley fishing                     |
| Gilded Blackstone Crate | Basalt Deltas fishing                        |
| End Crate               | End fishing                                  |

### Opening a Crate

1. Hold the crate item.
2. Sneak and use it while looking at air or a block.
3. The crate is consumed in Survival and its loot is dropped at the player's position.

Opening a crate does not create a chest GUI. Players in Creative do not consume the crate.

<hr style="border: 0; border-top: 2px solid #D43C3C;">

## Fish and Food

Many catches are edible, while others are crafting ingredients or direct sources of vanilla materials.

<div align="center">

_Screenshot placeholder — Show the fish collection or a group of prepared foods, with the most unusual fish effects
represented._

</div>

### Special Food Effects

| Item            | Nutrition | Effect or behavior                     |
|-----------------|----------:|----------------------------------------|
| Icicle Fish     |         2 | Applies Instant Harm when eaten        |
| Slimefish       |         4 | Applies Slowness for 5 seconds         |
| Charfish        |         2 | Applies Blindness for 5 seconds        |
| Smokey Salmon   |         6 | Applies Fire Resistance for 15 seconds |
| Magma Cod       |         6 | Applies Fire Resistance for 15 seconds |
| Thundering Bass |         5 | Applies Speed for 15 seconds           |
| Chorus Cod      |         6 | Uses vanilla Chorus Fruit behavior     |

### Fish-to-Resource Conversions

| Input               | Result       |
|---------------------|--------------|
| Bonefish            | 2 Bones      |
| Icicle Fish         | Blue Ice     |
| Blizzard Bass       | Ice          |
| 2 Blizzard Bass     | Packed Ice   |
| Lilyfish            | Lily Pad     |
| Slimefish           | Slime Ball   |
| Basalt Bass         | Basalt       |
| Magma Cod           | Magma Block  |
| Obsidian Halibut    | Obsidian     |
| Ender Eel           | Eye of Ender |
| Seaweed Eel         | 8 Seaweed    |
| Rainy Bass + Bucket | Water Bucket |

<hr style="border: 0; border-top: 2px solid #0784A8;">

## Recipes

Go Fish Rehooked uses standard Minecraft crafting and cooking stations.

<div align="center">

_Screenshot placeholder — Show a crafting or cooking setup for a rod, lure, custom meal, or fish-to-resource
conversion._

</div>

### Crafting Highlights

- **Blaze Rod:** 3 Blaze Rods and 2 String.
- **Diamond Reinforced Rod:** 4 Diamonds and 1 Fishing Rod.
- **Simple Lure:** 1 Feather and 4 Iron Nuggets.
- **Soul Lure:** 2 Soul Salmon and 1 Simple Lure.
- **Aquatic Astral Stew:** 1 of each full-moon fish and 1 Bowl.
- **Endfish n' Chorus:** 1 Baked Endfish and 2 Chorus Fruit.
- **Blackstone Trout Deluxe:** Baked Seaweed, Grilled Blackstone Trout, and Baked Potato.
- **End Crystal:** 2 Matrix Fish and 1 Iron Nugget.

Other rods, the Golden Fish, and all crates are obtained through fishing loot rather than custom crafting recipes.

<hr style="border: 0; border-top: 2px solid #29313D;">

## Commands and Advancements

### `/fish`

The `/fish` command is an operator utility for generating fishing loot without casting a hook.

```text
/fish
/fish <count>
```

The command requires permission level 2. Without an argument it generates one catch; `<count>` accepts an integer from 1
to 1000. Generated items are added directly to the executing player's inventory.

### Advancement Progression

Go Fish Rehooked adds a fishing-focused advancement tree, including:

- Fishing Journey
- Good Ol' Seaweed
- Suspicious Seaweed
- Seaweed Upgrades
- Fresh and Delicious
- Fishing under a Full Moon
- Stormy Seas
- The Great Golden Lure
- Galaxy's Revelation
- A Crate from Outer Space
- Simple Crates
- Crate Upgrades
- Fancy Crates
- Ultimate Fishing Rewards
- Humble Alternatives

<hr style="border: 0; border-top: 3px solid #0784A8;">

## Links

- [Go Fish Wiki](https://github.com/anviaan/go-fish/wiki)
- [Fishing](https://github.com/anviaan/go-fish/wiki/Fishing)
- [Fish and Food](https://github.com/anviaan/go-fish/wiki/Fish-and-Food)
- [Rods and Lures](https://github.com/anviaan/go-fish/wiki/Rods-and-Lures)
- [Crates](https://github.com/anviaan/go-fish/wiki/Crates)
- [Recipes](https://github.com/anviaan/go-fish/wiki/Recipes)
- [Commands and Advancements](https://github.com/anviaan/go-fish/wiki/Commands-and-Advancements)
