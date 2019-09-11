package edu.uchicago.cs.java.finalproject.mvc.model;

import java.awt.*;

/**
 * The Digit class is used to build numbers for displaying values like score, level, etc.
 * Each digit is represented by a 2D Color array of "pixels" similar to a digital display on a clock.
 * Created by terencezhao on 12/1/15.
 */
public class Digit {

    public static Color[][] pixels(int digit, Color color, Color other) {
        Color[][] pixels = new Color[5][3];
        switch(digit) {
            case 0:
                pixels = new Color[][] {
                        {color, color, color},
                        {color, other, color},
                        {color, other, color},
                        {color, other, color},
                        {color, color, color}
                };
                break;
            case 1:
                pixels = new Color[][] {
                        {other, color, other},
                        {other, color, other},
                        {other, color, other},
                        {other, color, other},
                        {other, color, other}
                };
                break;
            case 2:
                pixels = new Color[][] {
                        {color, color, color},
                        {other, other, color},
                        {color, color, color},
                        {color, other, other},
                        {color, color, color}
                };
                break;
            case 3:
                pixels = new Color[][] {
                        {color, color, color},
                        {other, other, color},
                        {color, color, color},
                        {other, other, color},
                        {color, color, color}
                };
                break;
            case 4:
                pixels = new Color[][] {
                        {color, other, color},
                        {color, other, color},
                        {color, color, color},
                        {other, other, color},
                        {other, other, color}
                };
                break;
            case 5:
                pixels = new Color[][] {
                        {color, color, color},
                        {color, other, other},
                        {color, color, color},
                        {other, other, color},
                        {color, color, color}
                };
                break;
            case 6:
                pixels = new Color[][] {
                        {color, color, color},
                        {color, other, other},
                        {color, color, color},
                        {color, other, color},
                        {color, color, color}
                };
                break;
            case 7:
                pixels = new Color[][] {
                        {color, color, color},
                        {other, other, color},
                        {other, other, color},
                        {other, other, color},
                        {other, other, color}
                };
                break;
            case 8:
                pixels = new Color[][] {
                        {color, color, color},
                        {color, other, color},
                        {color, color, color},
                        {color, other, color},
                        {color, color, color}
                };
                break;
            case 9:
                pixels = new Color[][] {
                        {color, color, color},
                        {color, other, color},
                        {color, color, color},
                        {other, other, color},
                        {other, other, color}
                };
                break;
            default:
                break;

        }
        return pixels;
    }
}
