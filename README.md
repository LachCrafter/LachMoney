# LachMoney [Work in progress]
> ⚠️ This plugin is currently not in the state of being production-ready! Current ETA is `April 12, 2025`.

LachMoney is a lightweight money Minecraft economy plugin with an extensive API.

## Features
- **SQL**: All data is handled through SQL, you can choose SQLite or MySQL
- **Customizability**: You can configure almost all parts of the plugin
- **Extensive API**: Integrate *LachMoney* in your plugins for a global economy


## Installation
1. Download the latest release of the plugin from the [Releases](https://github.com/LachCrafter/LachMoney/releases) section.
2. Place the downloaded JAR file into the `plugins` folder of your Minecraft server.
3. Restart your server to load and enable the plugin.
4. Configure the SQL Database and restart.

## Configuration
We split the configuration to multiple files.
- `config.yml` - for general configuration like start_money
- `database.yml` - for database configuration
- `messages.yml` - for message customization

## Commands
- `/lachmoney`: Displays information about the plugin and available commands.
- `/money`: Displays your current balance.
- `/pay <playername> <amount>`: Pay someone with a specific amount.
- `/lachmoney setBalance <playername> <amount>`: Explicitly set someone's balance.

## Contributing
Contributions are welcome! If you find a bug or want to suggest an enhancement, please create an issue in the [Issue Tracker](https://github.com/LachCrafter/LachMoney/issues).
If you'd like to contribute code, fork the repository, create a new branch, make your changes, and then create a pull request.

## License
This project is licensed under the [MIT License](https://github.com/LachCrafter/LachShield/blob/master/LICENSE).

## Contact
For questions or inquiries, you can reach out to me via email: [contact@lachcrafter.de](mailto:contact@lachcrafter.de) or by creating an issue.