package me.nashplugz.quantumh.utils;

import net.md_5.bungee.api.ChatColor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradientUtil {

    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<gradient:(#[A-Fa-f0-9]{6})(:#[A-Fa-f0-9]{6})*>(.+?)</gradient>");

    public static String applyGradient(String message) {
        Matcher matcher = GRADIENT_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String gradientContent = matcher.group(3);
            List<Color> colors = new ArrayList<>();

            String[] colorCodes = matcher.group(1).split(":");
            for (String colorCode : colorCodes) {
                colors.add(Color.decode(colorCode));
            }

            String gradientText = createGradient(gradientContent, colors);
            matcher.appendReplacement(buffer, gradientText);
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    private static String createGradient(String text, List<Color> colors) {
        StringBuilder result = new StringBuilder();
        int length = text.length();
        int colorCount = colors.size();

        for (int i = 0; i < length; i++) {
            float ratio = (float) i / (length - 1);
            int colorIndex = (int) (ratio * (colorCount - 1));
            Color color1 = colors.get(colorIndex);
            Color color2 = colors.get(Math.min(colorIndex + 1, colorCount - 1));

            float localRatio = (ratio * (colorCount - 1)) % 1;
            Color interpolatedColor = interpolateColor(color1, color2, localRatio);

            result.append(ChatColor.of(interpolatedColor)).append(text.charAt(i));
        }

        return result.toString();
    }

    private static Color interpolateColor(Color color1, Color color2, float ratio) {
        int red = (int) (color1.getRed() * (1 - ratio) + color2.getRed() * ratio);
        int green = (int) (color1.getGreen() * (1 - ratio) + color2.getGreen() * ratio);
        int blue = (int) (color1.getBlue() * (1 - ratio) + color2.getBlue() * ratio);
        return new Color(red, green, blue);
    }
}
