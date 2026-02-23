package fgc.PlayerInput;

/** <h1>These are the advanced action commands.</h1>
 * <p>>I.E action commands that require 1 or more button presses in sequence
 * to activate. The reason they are separated like this because these button combinations,
 * will be consumed in the actionQueue to execute these attacks</p>
 *
 * <h2>Outline of the inputs in combination</h2>
 * <ul>
 *     <li>Dash</li>
 *     <ul>
 *         <li>FORWARD->NEUTRAL->FORWARD(enters dash state)</li>
 *     </ul>
 *     <li>Parry</li>
 *     <ul>
 *         <li>Hold BLOCK->SPECIAL</li>
 *         <li>DOWN_BLOCK->SPECIAL</li>
 *     </ul>
 *     <li>Directional Attacks</li>
 *     <ul>
 *         <li>(FORWARD/BACK/UP/DOWN)->PUNCH</li>
 *         <li>(FORWARD/BACK/UP/DOWN)->KICK</li>
 *         <li>(FORWARD/BACK/UP/DOWN)->HEAVY</li>
 *         <li>(FORWARD/BACK/UP/DOWN)->SPECIAL</li>
 *     </ul>
 *     <li>Quarter Circles</li>
 *     <ul>
 *         <li>Quarter Circle Forward</li>
 *         <ul>
 *             <li>DOWN->DOWN_FORWARD->FORWARD</li>
 *         </ul>
 *         <li>Quarter Circle Back</li>
 *         <ul>
 *             <li>DOWN->DOWN_BACK->BACK</li>
 *         </ul>
 *         <li>Dragon Punch</li>
 *         <ul>
 *             <li>FORWARD->DOWN->DOWN_FORWARD</li>
 *         </ul>
 *         <li>Half Circle</li>
 *         <ul>
 *             <li>BACK->DOWN_BACK->DOWN->DOWN_FORWARD->FORWARD</li>
 *         </ul>
 *         <li>Charge State</li>
 *         <ul>
 *             <li>Hold (DOWN/BACK)</li>
 *             <li>Use attack input to CHARGE_EXECUTE, dependent on character</li>
 *         </ul>
 *         <li>METER_1 (Character Dependent)</li>
 *         <li>METER_2 (Character Dependent)</li>
 *         <li>METER_3 (Character Dependent)</li>
 *     </ul>
 * </ul>
 *
 * */


public enum ADVANCED_ACTION_COMMAND {
    PARRY,
    DASH,
    FORWARD_PUNCH,
    FORWARD_KICK,
    FORWARD_HEAVY,
    FORWARD_SPECIAL,
    DOWN_PUNCH,
    DOWN_KICK,
    DOWN_HEAVY,
    DOWN_SPECIAL,
    UP_PUNCH,
    UP_KICK,
    UP_HEAVY,
    UP_SPECIAL,
    BACK_PUNCH,
    BACK_KICK,
    BACK_HEAVY,
    BACK_SPECIAL,
    QUARTER_CIRCLE_FORWARD,
    QUARTER_CIRCLE_BACK,
    DRAGON_PUNCH,
    HALF_CIRCLE,
    CHARGE_STATE,
    CHARGE_EXECUTE,
    METER_1,
    METER_2,
    METER_3,
}
