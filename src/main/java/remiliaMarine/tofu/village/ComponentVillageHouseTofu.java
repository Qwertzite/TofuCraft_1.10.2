package remiliaMarine.tofu.village;

import java.util.Random;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.common.BiomeDictionary;
import remiliaMarine.tofu.TofuCraftCore;
import remiliaMarine.tofu.block.BlockLeek;
import remiliaMarine.tofu.init.TcBlocks;
import remiliaMarine.tofu.init.TcItems;
import remiliaMarine.tofu.init.TcVillages;
import remiliaMarine.tofu.util.ModLog;

/**
 * Tofu cook's House in village
 *
 * @author Tsuteto (original author)
 * @author RemiliaMarine (translator)
 */
public class ComponentVillageHouseTofu extends Village {

    public static final Item[] displayedItemList = new Item[]{
            TcItems.tofuKinu, TcItems.tofuMomen, TcItems.tofuMetal,
            TcItems.tofuZunda, TcItems.tofuMiso, TcItems.tofuStrawberry};

    public static IBlockState TOFU_ISHI = TcBlocks.tofuIshi.getDefaultState();
    public static IBlockState GLASS_PANE = Blocks.GLASS_PANE.getDefaultState();
    public static IBlockState AIR_BLOCK = Blocks.AIR.getDefaultState();
    public static IBlockState PLANKS = Blocks.PLANKS.getDefaultState();
    public static IBlockState OAK_STAIRS = Blocks.OAK_STAIRS.getDefaultState();
    public static IBlockState CARPET = Blocks.CARPET.getDefaultState();
    public static IBlockState TORCH_UP = Blocks.TORCH.getDefaultState();

    private StructureVillagePieces.Start startPiece;

    public ComponentVillageHouseTofu() {}

    public ComponentVillageHouseTofu(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random rand, StructureBoundingBox par4StructureBoundingBox, EnumFacing par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.startPiece = par1ComponentVillageStartPiece;
        this.setCoordBaseMode(par5);
        this.boundingBox = par4StructureBoundingBox;
    }

    public static StructureBoundingBox getStructureBoundingBox(int par3, int par4, int par5, EnumFacing par6)
    {
        return StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, -4, 0, 9, 6, 7, par6);
    }

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {

        ModLog.debug(startPiece);
        if (startPiece != null)
        {
            if (BiomeDictionary.isBiomeOfType(startPiece.biome, TofuCraftCore.BIOME_TYPE_TOFU)) return false;
        }

        if (this.averageGroundLvl < 0) // averageGroundLevel
        {
            this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

            if (this.averageGroundLvl < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
        }

        int width = 8;
        int height = 4;
        int length = 6;

        // Floor
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, width, 0, length, TOFU_ISHI, TOFU_ISHI, false);
        // Ceiling
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, width, height, length, TOFU_ISHI, TOFU_ISHI, false);

        // Wall
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0,     1, 0,      0,     height - 1, length, TOFU_ISHI, TOFU_ISHI, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, width, 1, 0,      width, height - 1, length, TOFU_ISHI, TOFU_ISHI, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0,     1, length, width, height - 1, length, TOFU_ISHI, TOFU_ISHI, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0,     1, 0,      width, height - 1, 0,      TOFU_ISHI, TOFU_ISHI, false);

        // Window
        this.setBlockState(worldIn, GLASS_PANE, 0, 2, length / 2 - 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, GLASS_PANE, 0, 2, length / 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, GLASS_PANE, 0, 2, length / 2 + 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, GLASS_PANE, width / 2 - 1, 2, length, structureBoundingBoxIn);
        this.setBlockState(worldIn, GLASS_PANE, width / 2, 2, length, structureBoundingBoxIn);
        this.setBlockState(worldIn, GLASS_PANE, width / 2 + 1, 2, length, structureBoundingBoxIn);
        this.setBlockState(worldIn, GLASS_PANE, width, 2, length / 2 - 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, GLASS_PANE, width, 2, length / 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, GLASS_PANE, width, 2, length / 2 + 1, structureBoundingBoxIn);

        // Door
        this.placeDoor(width / 2, 1, 0, EnumFacing.NORTH, false, Blocks.OAK_DOOR.getDefaultState(), randomIn, worldIn, structureBoundingBoxIn);

        if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock() == Blocks.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() != Blocks.AIR)
        {
            this.setBlockState(worldIn, TcBlocks.tofuStairsIshi.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), width / 2, 0, -1, structureBoundingBoxIn);
        }

        // Cleaning
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, width - 1, height - 1, length - 1, AIR_BLOCK, AIR_BLOCK, false);

        /*
         * Basement
         */
        // Room
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, -3, 1, width - 1, -1, length - 1, AIR_BLOCK, AIR_BLOCK, false);
        // Floor
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, -4, 0, width, -4, length, PLANKS, PLANKS, false);
        // Wall
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, -3, 0, 0, -1, length, PLANKS, PLANKS, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, width, -3, 0, width, -1, length, PLANKS, PLANKS, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, -3, length, width, -1, length, PLANKS, PLANKS, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, -3, 0, width, -1, 0, PLANKS, PLANKS, false);

        // Stairs
        IBlockState stairsSouth = OAK_STAIRS.withProperty(BlockStairs.FACING, EnumFacing.SOUTH);
        IBlockState stairsEast = OAK_STAIRS.withProperty(BlockStairs.FACING, EnumFacing.EAST);
        this.setBlockState(worldIn, AIR_BLOCK, width - 1, 0, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, AIR_BLOCK, width - 1, 0, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, AIR_BLOCK, width - 1, 0, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, AIR_BLOCK, width - 1, 0, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, stairsSouth, width - 1, 0, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, stairsSouth, width - 1, -1, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, stairsSouth, width - 1, -2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, PLANKS, width - 1, -1, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, PLANKS, width - 1, -2, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, PLANKS, width - 1, -3, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, PLANKS, width - 1, -1, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, PLANKS, width - 1, -2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, PLANKS, width - 1, -3, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, PLANKS, width - 1, -2, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, PLANKS, width - 1, -3, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, PLANKS, width - 1, -3, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, PLANKS, width - 1, -3, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, stairsEast, width - 2, -3, 5, structureBoundingBoxIn);

        /*
         * Interior
         */

        int i, j, k;

        // Carpet
        for (i = 1; i <= length - 1; ++i)
        {
            for (j = 1; j <= width - 2; ++j)
            {
                this.setBlockState(worldIn, CARPET, j, 1, i, structureBoundingBoxIn);
            }
        }
        this.setBlockState(worldIn, CARPET, width - 1, 1, 1, structureBoundingBoxIn);

        // Torch
        this.setBlockState(worldIn, TORCH_UP.withProperty(BlockTorch.FACING, EnumFacing.NORTH), width / 2, 3, 1, structureBoundingBoxIn);
        this.setBlockState(worldIn, TORCH_UP.withProperty(BlockTorch.FACING, EnumFacing.WEST), width - 2, -2, length / 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, TORCH_UP.withProperty(BlockTorch.FACING, EnumFacing.EAST), 1, -2, length / 2, structureBoundingBoxIn);

        // Workbench
        this.setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 2, 1, 5, structureBoundingBoxIn);
        // Salt Furnace
        this.setBlockState(worldIn, TcBlocks.saltFurnaceIdle.getDefaultState(), 1, 1, 5, structureBoundingBoxIn);
        // Cauldron
        this.setBlockState(worldIn, Blocks.CAULDRON.getDefaultState(), 1, 2, 5, structureBoundingBoxIn);
        // Book shelf
        IBlockState bookShelf = Blocks.BOOKSHELF.getDefaultState();
        this.setBlockState(worldIn, bookShelf, 6, 1, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, bookShelf, 6, 1, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, bookShelf, 6, 1, 5, structureBoundingBoxIn);
        this.setBlockState(worldIn, bookShelf, 6, 2, 3, structureBoundingBoxIn);
        this.setBlockState(worldIn, bookShelf, 6, 2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, bookShelf, 6, 2, 5, structureBoundingBoxIn);

        // Item frame
        k = 0;
        for (j = 3; j >= 2; j--)
        {
            for (i = 1; i <= 3; i++)
            {
                this.hangItemFrame(worldIn, structureBoundingBoxIn, i, j, 1, new ItemStack(displayedItemList[k++]));
            }
        }

        // Tofu
        IBlockState tofuMomen = TcBlocks.tofuMomen.getDefaultState();
        this.setBlockState(worldIn, tofuMomen, 2, -3, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, tofuMomen, 3, -3, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, tofuMomen, 4, -3, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, tofuMomen, 5, -3, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, tofuMomen, 2, -3, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, tofuMomen, 3, -3, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, tofuMomen, 4, -3, 4, structureBoundingBoxIn);
        // Weight
        IBlockState cobblestone = Blocks.COBBLESTONE.getDefaultState();
        this.setBlockState(worldIn, cobblestone, 2, -2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, cobblestone, 3, -2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, cobblestone, 4, -2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, cobblestone, 5, -2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, cobblestone, 2, -2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, cobblestone, 3, -2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, cobblestone, 4, -2, 4, structureBoundingBoxIn);

        // Leek
        IBlockState leek = TcBlocks.leek.getDefaultState().withProperty(BlockLeek.META, BlockLeek.META_NATURAL);
        for (i = 0; i < 20; i++)
        {
            j = randomIn.nextInt(width - 1) + 1;
            k = randomIn.nextInt(length - 1) + 1;

            this.setBlockState(worldIn, leek, j, height + 1, k, structureBoundingBoxIn);
        }

        for (i = 0; i <= length; ++i)
        {
            for (j = 0; j <= width; ++j)
            {
                this.clearCurrentPositionBlocksUpwards(worldIn, j, height + 2, i, structureBoundingBoxIn);
                this.replaceAirAndLiquidDownwards(worldIn, cobblestone, j, -5, i, structureBoundingBoxIn); // fillCurrentPositionBlocksDownwards
            }
        }

        this.spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
        return true;
	}

    protected void hangItemFrame(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, ItemStack item)
    {
        int j1 = this.getXWithOffset(par3, par5);
        int k1 = this.getYWithOffset(par4);
        int l1 = this.getZWithOffset(par3, par5);
        BlockPos pos = new BlockPos(j1, k1, l1);

        if (!par2StructureBoundingBox.isVecInside(pos))
        {
            return;
        }

        EnumFacing facing = this.getCoordBaseMode();
        EntityItemFrame itemFrame = new EntityItemFrame(par1World, pos, facing);
        itemFrame.setDisplayedItem(item);

        if (itemFrame.onValidSurface())
        {
            par1World.spawnEntityInWorld(itemFrame);
        } else {
            par1World.setBlockState(pos, Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, facing), 2);
        }
    }

    @Override
    @Deprecated // Use Forge version below.
    protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
    {
        return currentVillagerProfession;
    }

	@Override
	protected net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession chooseForgeProfession(int count, net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession prof)
    {
        return TcVillages.ProfessionTofuCook;
    }

	/**
	 *  copied from MOD "GuerrillaCity"
	 *  (A bit modified)
	 */
    protected void placeDoor(int x, int y, int z, EnumFacing facing, boolean isReversed, IBlockState doorState, Random rand, World worldIn, StructureBoundingBox structureBoundingBoxIn) {

    	if(isReversed) { doorState = doorState.withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.RIGHT); }
    	else {doorState = doorState.withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT); }

        this.setBlockState(worldIn, doorState.withProperty(BlockDoor.FACING, facing), x, y, z, structureBoundingBoxIn);
        this.setBlockState(worldIn, doorState.withProperty(BlockDoor.FACING, facing).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), x, y + 1, z, structureBoundingBoxIn);
    }

}
