package bot.commands.moderation;

import bot.command.CommandContext;
import bot.command.ICommand;
import bot.utils.ModerationUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VUnMuteCommand extends ICommand {

    public VUnMuteCommand() {
        this.name = "vunmute";
        this.help = "unmute voice of the mentioned user on this guild";
        this.usage = "<@member> [reason]";
        this.minArgsCount = 1;
        this.userPermissions = new Permission[]{Permission.VOICE_MUTE_OTHERS};
        this.botPermissions = new Permission[]{Permission.VOICE_MUTE_OTHERS};
    }

    @Override
    public void handle(@NotNull CommandContext ctx) {
        final Message message = ctx.getMessage();
        final List<String> args = ctx.getArgs();

        if (message.getMentionedMembers().isEmpty()) {
            ctx.reply("Please @mention the member you want to unmute!");
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if (!ModerationUtils.canInteract(ctx.getMember(), target, "voice unmute", ctx.getChannel())) {
            return;
        }

        final String reason = String.join(" ", args.subList(1, args.size()));

        ModerationUtils.vunmute(message, target, reason);

    }

}
