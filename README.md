# 🤖 PuroSelosWalangLabel.java

> _"Di mo mahuli, pero ramdam mo."_

PuroSelosWalangLabel.java is a Robocode robot created for a university project, designed with a mix of evasive movement and aggressive finishing tactics. Inspired by hugot culture, the bot’s name reflects its behavior: elusive yet impactful.

## 💡 About the Bot

- **Strategy**: A hybrid of dodging and ramming.

  - **Movement**: Uses a modified Stop-and-Go strategy (`umiwasSaCommitmentMovement`) to dodge incoming fire dynamically.
  - **Targeting**: Circular targeting with predictive aiming and adaptive bullet power.
  - **Kill Secure**: When the enemy's energy is low, the bot attempts a ramming finisher using the `ramNaKungRamSystem`.

- **Customization**: Includes a separate color manager to stylize the robot.

- **Design Principles**:
  - Modular components for radar, targeting, movement, and ramming.
  - Clean and commented code for better readability and maintenance.

---

## 🚀 Getting Started

Follow the steps below to run the robot inside the Robocode environment.

### 1. Clone the Repository

```bash
git clone https://github.com/dfntlygeorge/puro-selos-walang-label-robot.git
```

> Replace `your-username` with your GitHub handle.

### 2. Place Inside Robocode Folder

Copy the cloned folder into your local `robots` directory inside your Robocode installation. Example:

```
Robocode/
└── robots/
    └── PuroSelosWalangLabel/
        └── (Java files here)
```

### 3. Compile the Bot

1. Open the **Robocode GUI**.
2. Click on **Robot -> Editor** to open the Robot Editor.
3. Use **File -> Open** and select `PuroSelosWalangLabel.java`.
4. Press **Compile -> Compile** to build the robot.

You should now see `PuroSelosWalangLabel` available in your robot list!
