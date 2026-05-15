public enum SpacecraftModel {
    ROCKET("Rocket", "Icons/rocket.png"){
        @Override
        public Spacecraft create(int panelWidth, int panelHeight, Earth earth, Moon moon){
            return new Rocket(panelWidth, panelHeight, earth, moon, new RocketMovement(earth, moon));
        }
    },
    SHUTTLE("Shuttle", "Icons/shuttle.png"){
        @Override
        public Spacecraft create(int panelWidth, int panelHeight, Earth earth, Moon moon){
            return new Shuttle(panelWidth, panelHeight, earth, moon, new ShuttleMovement(earth, moon));
        }
    },
    PROBE("Probe", "Icons/probe.png"){
        @Override
        public Spacecraft create(int panelWidth, int panelHeight, Earth earth, Moon moon){
            return new Probe(panelWidth, panelHeight, earth, moon, new ProbeMovement(earth, moon));
        }
    },
    LANDER("Lander", "Icons/lander.png"){
        @Override
        public Spacecraft create(int panelWidth, int panelHeight, Earth earth, Moon moon){
            return new Lander(panelWidth, panelHeight, earth, moon, new LanderMovement(earth, moon));
        }
    };

    private final String displayName;
    private final String iconPath;

    SpacecraftModel(String displayName, String iconPath){
        this.displayName = displayName;
        this.iconPath = iconPath;
    }

    public String getDisplayName(){
        return displayName;
    }
    
    public String getIconPath(){
        return iconPath;
    }
    
    public abstract Spacecraft create(int panelWidth, int panelHeight, Earth earth, Moon moon);
}