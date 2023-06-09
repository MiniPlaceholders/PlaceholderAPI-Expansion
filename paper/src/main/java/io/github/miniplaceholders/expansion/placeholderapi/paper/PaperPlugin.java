package io.github.miniplaceholders.expansion.placeholderapi.paper;

import io.github.miniplaceholders.api.utils.LegacyUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.miniplaceholders.api.Expansion;
import net.kyori.adventure.text.minimessage.tag.Tag;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.*;

@SuppressWarnings("unused")
public final class PaperPlugin extends JavaPlugin {

    @Override
    public void onEnable(){
        this.getSLF4JLogger().info("Starting PlaceholderAPI Expansion for MiniPlaceholders Paper");

        Expansion.builder("placeholderapi")
            .filter(Player.class)
            .globalPlaceholder("global", (queue, ctx) -> {
                final String argument = queue.popOr("You need to provide a placeholder").value();
                final String papiParsed = PlaceholderAPI.setPlaceholders(
                        null,
                        argument
                ).replace(SECTION_CHAR, AMPERSAND_CHAR);

                if (parseString(queue)) {
                    return Tag.preProcessParsed(miniMessage().serialize(LegacyUtils.parsePossibleLegacy(papiParsed)));
                } else {
                    return Tag.selfClosingInserting(LegacyUtils.parsePossibleLegacy(papiParsed));
                }
            })
            .audiencePlaceholder("player", (aud, queue, ctx) -> {
                final String argument = queue.popOr("You need to provide a placeholder").value();
                final String papiParsed = PlaceholderAPI.setPlaceholders(
                        (Player)aud,
                        argument
                ).replace(SECTION_CHAR, AMPERSAND_CHAR);

                if (parseString(queue)) {
                    return Tag.preProcessParsed(miniMessage().serialize(LegacyUtils.parsePossibleLegacy(papiParsed)));
                } else {
                    return Tag.selfClosingInserting(LegacyUtils.parsePossibleLegacy(papiParsed));
                }
            })
            .relationalPlaceholder("relational", (player, toShow, queue, ctx) -> {
                final String argument = queue.popOr("You need to provide a placeholder").value();
                final String papiParsed = PlaceholderAPI.setRelationalPlaceholders(
                        (Player) player,
                        (Player) toShow,
                        argument
                ).replace(SECTION_CHAR, AMPERSAND_CHAR);

                if (parseString(queue)) {
                    return Tag.preProcessParsed(miniMessage().serialize(LegacyUtils.parsePossibleLegacy(papiParsed)));
                } else {
                    return Tag.selfClosingInserting(LegacyUtils.parsePossibleLegacy(papiParsed));
                }
            })
            .build()
            .register();
    }

    private boolean parseString(ArgumentQueue queue) {
        return queue.hasNext() && queue.pop().lowerValue().equals("string");
    }
}
