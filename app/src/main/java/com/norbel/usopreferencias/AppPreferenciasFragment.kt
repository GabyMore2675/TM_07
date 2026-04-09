package com.norbel.usopreferencias

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.norbel.galeria.Galeria

class AppPreferenciasFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.misapppreferencias, rootKey)

        val segundaPantalla = findPreference<Preference>("secondPrefScreenPref")

        segundaPantalla?.setOnPreferenceClickListener {
            val intent = Intent(requireContext(), Galeria::class.java)
            startActivity(intent)
            true
        }
    }
}