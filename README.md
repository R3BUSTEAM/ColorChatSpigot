# WelPlug

WelPlug is a powerful and flexible plugin designed for Minecraft servers, enhancing player experience and server management. This plugin allows server administrators to customize their server interactions, manage player connections, and streamline gameplay.

## Features

- **Custom Welcome Messages**: Personalize the welcome messages players receive upon joining.
- **Player Management**: Easily manage player permissions, roles, and connections.
- **Event Listeners**: Respond to various in-game events, such as player joins, leaves, and other actions.
- **Configuration Options**: Extensive configuration options to tailor the plugin to your server's needs.
- **Compatibility**: Works seamlessly with popular Minecraft server versions.

## Installation

1. Download the latest version of WelPlug from the [Releases](#) page.
2. Place the `WelPlug.jar` file in the `plugins` folder of your Minecraft server.
3. Restart the server to enable the plugin.
4. Configure the settings in `plugins/WelPlug/config.yml` to customize your experience.

## Configuration

The `config.yml` file allows you to customize various aspects of WelPlug. Below is a sample configuration:

```yaml
welcome-message: "Welcome to our server, %player%! Enjoy your stay!"
enable-player-management: true
