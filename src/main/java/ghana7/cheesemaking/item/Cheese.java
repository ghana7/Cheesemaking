package ghana7.cheesemaking.item;

import ghana7.cheesemaking.CheesemakingMod;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;

public abstract class Cheese extends Item {
    public enum EnvironmentType {CAVE, SKY, BUILDING, LAND, NETHER}
    private int tier;
    public Cheese(int hunger, float saturation, int tier) {
        super(new Item.Properties()
                .food((new Food.Builder())
                        .hunger(hunger)
                        .saturation(saturation)
                        .build())
                .group(ItemGroup.FOOD));
        this.tier = tier;
    }

    public Cheese(Food.Builder builder, int tier) {
        super(new Item.Properties()
        .food(builder.build())
        .group(ItemGroup.FOOD)
        );
        this.tier = tier;
    }

    public Item getAged(EnvironmentType ageEnvironment) {
        return CheesemakingMod.FLAWED_CHEESE.get();
    }
}
