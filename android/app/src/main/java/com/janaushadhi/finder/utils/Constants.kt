package com.janaushadhi.finder.utils

object Constants {
    // ── Networking ──────────────────────────────────────────────────────────
    // ✅  PRODUCTION — Render.com free tier (auto-deploys from GitHub)
    const val BASE_URL = "https://jan-aushadhi.onrender.com/"  // ← Production (active)
    // const val BASE_URL = "http://10.0.2.2:3000/"            // ← Emulator
    // const val BASE_URL = "http://10.224.88.138:3000/"        // ← Physical device (LAN)

    // ── Gemini AI ───────────────────────────────────────────────────────────
    // Get your FREE key
    const val GEMINI_API_KEY = "AIzaSyAtKm9dqLEjBQsCi_bSmHb_1meh7y5tpSo"
    const val GEMINI_MODEL   = "gemini-1.5-flash"

    // ── SharedPreferences ───────────────────────────────────────────────────
    const val PREFS_NAME       = "jan_aushadhi_prefs"
    const val KEY_ONBOARDED    = "onboarded"

    // ── WorkManager ─────────────────────────────────────────────────────────
    const val REFILL_WORK_TAG    = "refill_check_work"
    const val NOTIF_CHANNEL_ID   = "refill_reminders"
    const val NOTIF_CHANNEL_NAME = "Refill Reminders"
    const val REFILL_NOTIF_ID    = 1001

    // ── Map Configuration ────────────────────────────────────────────────────
    const val DEFAULT_LAT = 19.0760   // Mumbai
    const val DEFAULT_LNG = 72.8777
    const val DEFAULT_ZOOM = 14.0

    // ── Room ─────────────────────────────────────────────────────────────────
    const val DB_NAME = "jan_aushadhi.db"
    const val DB_VERSION = 1

    // ── Fuzzy Search ─────────────────────────────────────────────────────────
    const val MAX_LEVENSHTEIN_DISTANCE = 2
    const val MIN_QUERY_LENGTH = 2
}
