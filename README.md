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
# Welplug Configuration File
# This file controls the settings for the Welplug plugin.

# General settings
settings:
  plugin-version: "1.0.0"
  enable-welcome-messages: true
  welcome-message: "Welcome to the server, %player%! Enjoy your stay!"
  farewell-message: "Goodbye, %player%! We hope to see you again soon!"
  
# Welcome messages configuration
welcome-messages:
  message1: "Welcome back, %player%! Ready for another adventure?"
  message2: "Hey %player%! The server missed you!"
  message3: "Greetings %player%! Let’s make some memories today!"

# Player settings
player-settings:
  enable-personalized-messages: true
  default-prefix: "[Player]"
  custom-prefix: "%player%'s Prefix" # Customize prefix for each player
  notify-on-join: true # Notify players when a friend joins

# Command settings
commands:
  enable-commands: true
  command-list:
    - "/welcome" # Command to display the welcome message
    - "/farewell" # Command to display the farewell message
    - "/setprefix <prefix>" # Command to set a custom prefix
    - "/reloadwelplug" # Command to reload the config file

# Permissions settings
permissions:
  default-permission: "welplug.use"
  admin-permission: "welplug.admin"

# Logging settings
logging:
  enable-logging: true
  log-file: "plugins/Welplug/logs/welplug.log"
  log-level: "INFO" # Possible levels: DEBUG, INFO, WARNING, ERROR

# Customization settings
customization:
  enable-color-codes: true # Allow color codes in messages
  default-message-color: "&7" # Default color for messages

