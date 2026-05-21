# FakePayPol

A Minecraft 1.21.1 Fabric mod that allows you to forge payment messages on DonutSMP 

##  Features
<img width="311" height="213" alt="image" src="https://github.com/user-attachments/assets/efbdb3b4-93c4-4482-be07-990b0f367528" />

- **Three Modes:**
  - **YOU PAID:** Generate a message saying you paid someone.
  - **PAID YOU:** Generate a message saying someone paid you.
  - **/PAY:** Intercepts actual `/pay` commands and turns them into fake messages locally.

##  Installation

1. **Install Fabric:** Ensure you have the [Fabric Loader](https://fabricmc.net/use/) installed for Minecraft **1.21.11**.
2. **Dependencies:** You will need the **Fabric API** mod in your `mods` folder.
3. **Add the Mod:** Download the `fakepaypol.jar` and place it into your Minecraft `.minecraft/mods` directory.
4. **Launch:** Start Minecraft using the Fabric profile.

##  How to Use

1. **Open Configuration:** Press **`B`** to open the GUI.
2. **Setup User & Amount:**
   - Type the **Username** and **Amount** into the dark boxes.
3. **Select Mode:**
   - **YOU PAID / PAID YOU:** Select one of these and press **`Alt + B`** in-game to instantly send the forged message to your chat.
   - **/PAY:** Toggle this mode to intercept the `/pay <user> <amount>` command. When active, typing the command in chat will play a "ding" sound and show the fake message instead of sending money.

##  Credits

- created and maintained by **pol**.
