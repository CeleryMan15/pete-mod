package com.celeryman.item;

import com.celeryman.Pete;
import com.celeryman.item.custom.PeteStickItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final FoodComponent PETE_ITEM_FOOD_COMPONENT = new FoodComponent.Builder()
            .alwaysEdible()
            // The duration is in ticks, 20 ticks = 1 second
            .build();

    public static final Item PETE_ITEM = register(
            "pete",
            Item::new,
            new Item.Settings().food(PETE_ITEM_FOOD_COMPONENT)
    );

    public static final Item PETE_STICK = register(
            "pete_stick",
            PeteStickItem::new,
            new Item.Settings()
    );

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Pete.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
        // Get the event for modifying entries in the ingredients group.
        // And register an event handler that adds our suspicious item to the ingredients group.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> {
                    itemGroup.add(ModItems.PETE_ITEM);
                    itemGroup.add(ModItems.PETE_STICK);
                });
    }

}
