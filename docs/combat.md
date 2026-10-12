# Combat

[Documentation index](README.md)

## Combat Mode

K toggles Combat Mode, built for fights with more than one attacker, where
re-targeting by hand between hits is not practical. It has two styles, chosen in
Settings (Combat Mode Style).

**Lock On** keeps you facing the nearest hostile mob, switching target as the
fight moves. You can still walk, strafe and jump freely while your aim stays
locked. Turning it on disables Build Mode if that was on.

**Beacon** leaves the camera entirely yours and guides you by sound:

- The nearest hostile becomes a cow bell ping that plays from its direction.
- The pings repeat faster the closer your facing is to it. Facing away is slow
  pings and facing it is fast ones, so ahead and behind sound different even
  though they pan the same.
- Pitch tells you whether to look up (lower) or down (higher).
- Once you are aimed at it in both directions the pings give way to a solid tone,
  which holds until your aim drifts off.
- Turning Beacon on cancels nothing: Build Mode, Scanner lock and the rest keep
  running.

Mobs that only fight once provoked are left alone until they are. A calm
Enderman is ignored, and so is a calm zombified piglin, since hitting one turns
the whole group on you. A zombified piglin counts once it is coming for you,
which any client can see, so this works on every server.

An **attack-ready cue** plays the moment your weapon's attack-strength meter
refills, so you know when a hit will land at full strength. Settings sets it to
Combat Mode only, always, or never.

## Bow Beacon

An optional setting, off by default. While you draw a bow, hold a loaded
crossbow, or hold back a trident, it guides your aim by sound. It uses the same
pings and solid tone as Combat Mode's Beacon, but points them at where the
*arrow* has to go rather than at the target: the pitch accounts for the arc,
which flattens as you draw further, and a moving target is led.

The solid tone means a shot from where you are aiming would really hit. The mod
flies the arrow along your actual aim with its real speed, gravity and the
target's movement, so the tone sounds only when the arrow arrives before
anything solid stops it. No line of sight, an under-drawn bow that would fall
short, or an arc that clips a wall all keep it silent.

The target is the Scanner's locked mob if you have one, otherwise the nearest
visible hostile mob to where you are already aiming. While Bow Beacon is on, the
Scanner lock and Combat Mode's Lock On stop steering the camera whenever you hold
a bow or crossbow (or hold back a trident), so the camera is yours to turn toward
the sound. The lock is kept and steers again when you put the weapon away. Bow
Beacon takes over from Combat Mode's Beacon style while you are aiming.

## Hostile Radar

Always on. When a hostile mob gets close with a clear line of sight to you, a
bell chime and narration (name, distance, direction) tell you at once, so a threat
is known before it is on top of you.

A separate melee-range alert plays a quick click every half-second while a
hostile mob is within melee reach, with no narration, so you know whether
something is close enough to hit or be hit without another sentence competing
with the fight. Settings can turn it off independently.
