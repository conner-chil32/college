package fgc.PlayerInput;

/**
 * <h1><u>Direction Inputs:</u></h1>
 * <p>The movement inputs are in 8 directions, with 1 neutral direction
 * when no direction input is held.</p>
 * <table>
 *     <tr>
 *         <td style="text-align: left">UP_BACK</td>
 *         <td style="text-align: center">UP</td>
 *         <td style="text-align: right">UP_FORWARD</td>
 *     </tr>
 *     <tr>
 *         <td style="text-align: left">BACK</td>
 *         <td style="text-align: center">NEUTRAL</td>
 *         <td style="text-align: right">FORWARD</td>
 *     </tr>
 *     <tr>
 *         <td style="text-align: left">DOWN_BACK</td>
 *         <td style="text-align: center">DOWN</td>
 *         <td style="text-align: right">DOWN_FORWARD</td>
 *     </tr>
 * </table>
 *
 * <h1><u>Basic Commands:</u></h1>
 * <p>These commands will be executed with single button presses</p>
 * <ul>
 *     <li>PUNCH</li>
 *     <li>KICK</li>
 *     <li>HEAVY</li>
 *     <li>SPECIAL</li>
 *     <li>BLOCK</li>
 * </ul>
 *
 * <p>Look at {@link ADVANCED_ACTION_COMMAND The Advanced Action Command ENUM} for
 * any action commands that require more than a single input or a combination of inputs.
 * There is some crossover with specific input direction commands requiring more than 1 button
 * press, but it specified here instead because it will not be consumed by in the actionQueue the
 * same way the advanced action commands are handled.</p>
 * */

public enum BASIC_ACTION_COMMANDS {
    NEUTRAL,
    FORWARD,
    BACK,
    UP,
    DOWN,
    UP_BACK,
    UP_FORWARD,
    DOWN_BACK,
    DOWN_FORWARD,
    PUNCH,
    KICK,
    HEAVY,
    SPECIAL,
    BLOCK,
    DOWN_BLOCK
}
