package ghana7.cheesemaking.item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public abstract class Cheese extends Item {
    public Cheese(int hunger, float saturation) {
        super(new Item.Properties()
                .food((new Food.Builder())
                        .hunger(hunger)
                        .saturation(saturation)
                        .build())
                .group(ItemGroup.FOOD));
    }
}
