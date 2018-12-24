package mcp.mobius.waila.api.event;

import mcp.mobius.waila.api.IWailaCommonAccessor;
import net.fabricmc.fabric.util.HandlerArray;
import net.fabricmc.fabric.util.HandlerRegistry;
import net.minecraft.text.TextComponent;

import java.util.List;

/**
 * This event is fired just before the Waila tooltip sizes are calculated. This is the last chance to make edits to
 * the information being displayed.
 * <p>
 * This event is not cancelable.
 * <p>
 * {@link #currentTip} - The current tooltip to be drawn.
 */
public class WailaTooltipEvent {

    public static final HandlerRegistry<HandleTooltip> WAILA_HANDLE_TOOLTIP = new HandlerArray<>(HandleTooltip.class);

    private final List<TextComponent> currentTip;
    private final IWailaCommonAccessor accessor;

    public WailaTooltipEvent(List<TextComponent> currentTip, IWailaCommonAccessor accessor) {
        this.currentTip = currentTip;
        this.accessor = accessor;
    }

    public List<TextComponent> getCurrentTip() {
        return currentTip;
    }

    public IWailaCommonAccessor getAccessor() {
        return accessor;
    }

    public interface HandleTooltip {
        void onTooltip(WailaTooltipEvent event);
    }
}