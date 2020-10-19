/**
 * Copyright 2020 WaterdogTEAM
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pe.waterdog.command.defaults;

import pe.waterdog.command.Command;
import pe.waterdog.command.CommandSender;
import pe.waterdog.command.CommandSettings;
import pe.waterdog.network.ServerInfo;
import pe.waterdog.player.ProxiedPlayer;
import pe.waterdog.utils.types.TextContainer;

public class ServerCommand extends Command {

    public ServerCommand() {
        super("server", CommandSettings.builder()
                .setDescription("waterdog.command.server.description")
                .setUsageMessage("waterdog.command.server.usage")
                .setPermission("waterdog.command.server.permission")
                .build());
    }

    @Override
    public boolean onExecute(CommandSender sender, String alias, String[] args) {
        if (args.length < 1 || !sender.isPlayer() && args.length < 2){
            return false;
        }

        ServerInfo server = sender.getProxy().getServerInfo(args[0]);
        if (server == null){
            sender.sendMessage("§cServer not found!");
            return true;
        }

        ProxiedPlayer player;
        if (sender.isPlayer() && args.length < 2){
            player = (ProxiedPlayer) sender;
        }else {
            player = sender.getProxy().getPlayer(args[1]);
            if (player == null) {
                sender.sendMessage("§cPlayer not found!");
                return true;
            }
            sender.sendMessage(new TextContainer("§aPlayer {%0} was sent to {%1} server!", player.getName(), server.getServerName()));
        }

        player.connect(server);
        return true;
    }
}