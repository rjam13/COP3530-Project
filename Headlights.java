import java.time.LocalDateTime;
import java.util.Random;

public class CarWeatherSensor {

    private static class Weather {
        private String condition;
        private int temperature;
        private boolean isNight;

        public Weather(String condition, int temperature) {
            this.condition = condition;
            this.temperature = temperature;
            this.isNight = isNight();
        }

        public String getCondition() {
            return condition;
        }

        public double getTemperature() {
            return temperature;
        }

        public boolean isNight() {
            LocalDateTime now = LocalDateTime.now();
            int hour = now.getHour();
            return hour >= 18 || hour < 6;
        }
    }

    private static class TreeNode {
        private String condition;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(String condition) {
            this.condition = condition;
            this.left = null;
            this.right = null;
        }

    public void insertLeft(TreeNode node) {
        this.left = node;
    }

    public void insertRight(TreeNode node) {
        this.right = node;
    }

    public boolean evaluate(Weather weather) {
        switch (condition) {
            case "Raining":
                return weather.getCondition().equals("Raining");
            case "Snowing":
                return weather.getCondition().equals("Snowing");
            case "Night":
                return weather.isNight();
            case "Poor visibility":
                return weather.getCondition().equals("Rainy") || weather.getCondition().equals("Snowy");
            case "Headlights on":
                return true;
            default:
                throw new IllegalArgumentException("Invalid condition: " + condition);
        }
    }
}

    private static Weather generateRandomWeather() {
        Random rand = new Random();
        String[] conditions = {"Sunny", "Cloudy", "Rainy", "Snowy"};
        String condition = conditions[rand.nextInt(conditions.length)];
        int temperatureCelsius = rand.nextInt(121) - 10;
        int temperatureFahrenheit = temperatureCelsius * 9 / 5 + 32;
        temperatureFahrenheit = Math.min(temperatureFahrenheit, 98);
        Weather weather = new Weather(condition, temperatureFahrenheit);
        boolean isNight = weather.isNight();
        weather.isNight = isNight;
        return weather;
    }

    public static boolean evaluateTree(TreeNode node, Weather weather) {
        if (node == null) {
            return false;
        }
        if (node.left == null && node.right == null) {
            return node.evaluate(weather);
        }
        boolean left = evaluateTree(node.left, weather);
        boolean right = evaluateTree(node.right, weather);
        if (node.condition.equals("Poor visibility") && (left || right)) {
            return node.evaluate(weather);
        }
        return left || right;
    }

    public static void main(String[] args) {
        System.out.println("Ford Weather System Sucsessfully Activated");
            
        Weather weather = generateRandomWeather();
            System.out.print("Current weather: " + weather.getCondition());
            System.out.println(", Temperature: " + weather.getTemperature());

            TreeNode rainyNode = new TreeNode("Raining");
            TreeNode snowyNode = new TreeNode("Snowing");
            TreeNode nightNode = new TreeNode("Night");
            TreeNode poorVisibilityNode = new TreeNode("Poor visibility");
            TreeNode headlightsOnNode = new TreeNode("Headlights on");

            rainyNode.insertLeft(headlightsOnNode);
            snowyNode.insertLeft(headlightsOnNode);
            nightNode.insertLeft(headlightsOnNode);

            poorVisibilityNode.insertLeft(headlightsOnNode);
            poorVisibilityNode.insertRight(rainyNode);

            TreeNode root = poorVisibilityNode;

            boolean shouldTurnOnHeadLights = evaluateTree(root, weather);

            if (shouldTurnOnHeadLights) {
                System.out.println("Inclement weather detected, headlights sucssessfully activated");
            }
    }
}
