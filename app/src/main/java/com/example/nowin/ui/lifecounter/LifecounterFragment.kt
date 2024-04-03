package com.example.nowin.ui.lifecounter

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.nowin.R
import com.example.nowin.databinding.FragmentLifecounterBinding

class LifecounterFragment : Fragment() {

    private var _binding: FragmentLifecounterBinding? = null
    private val binding get() = _binding!!

    private var numPlayers: Int = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLifecounterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Prompt user to select the number of players
        showPlayerCountDialog()

        return root
    }

    private fun showPlayerCountDialog() {
        val options = arrayOf("2", "3", "4", "5", "6", "7", "8")
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Select Number of Players")
            .setItems(options) { _, which ->
                numPlayers = options[which].toInt()
                // Generate UI elements for the selected number of players
                generatePlayerUI(numPlayers)
            }
            .setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }


    private fun generatePlayerUI(numPlayers: Int) {
        // Clear existing player UI elements if needed
        binding.playerContainer.removeAllViews()

        // Calculate the number of rows and columns for the grid layout
        val numRows = when (numPlayers) {
            2 -> 2
            4 -> 2
            else -> (numPlayers + 1) / 2
        }
        val numColumns = if (numPlayers > 2) 2 else 1

        // Create a grid layout
        val gridLayout = GridLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            rowCount = numRows
            columnCount = numColumns
        }

        // Generate player UI elements
        for (rowIndex in 0 until numRows) {
            val horizontalLayout = LinearLayout(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                orientation = LinearLayout.HORIZONTAL
            }

            for (colIndex in 0 until numColumns) {
                val playerIndex = rowIndex * numColumns + colIndex + 1
                if (playerIndex <= numPlayers) {
                    val playerLayout = createPlayerLayout(playerIndex)
                    val params = LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    playerLayout.layoutParams = params
                    horizontalLayout.addView(playerLayout)
                }
            }
            gridLayout.addView(horizontalLayout)
        }

        // Add the grid layout to the main container
        binding.playerContainer.addView(gridLayout)
    }




    private fun createPlayerLayout(playerNumber: Int): View {
        // Inflate the player layout
        val playerView = layoutInflater.inflate(R.layout.player_item, null)

        // Find views in the player layout
        val playerTextView: TextView = playerView.findViewById(R.id.playerTextView)
        val incrementButton: Button = playerView.findViewById(R.id.incrementButton)
        val decrementButton: Button = playerView.findViewById(R.id.decrementButton)

        // Customize views
        playerTextView.text = "Player $playerNumber"

        // Set onClickListeners for buttons
        incrementButton.setOnClickListener {
            // Implement logic to increment life total for the current player
        }
        decrementButton.setOnClickListener {
            // Implement logic to decrement life total for the current player
        }

        return playerView
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
