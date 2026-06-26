package com.example.skillflow.ui.fragment.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.skillflow.R
import com.example.skillflow.core.utils.Constants
import com.example.skillflow.databinding.FragmentLoginBinding
import com.example.skillflow.ui.viewModel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFilter()
        setupTextWatchers()
        observeViewModel()
        setupButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupFilter() = with(binding) {
        val filter = InputFilter { source, _, _, _, _, _ ->
            if (source.toString().matches(Regex("[а-яА-ЯёЁ]"))) "" else null
        }
        edtEmail.filters = arrayOf(filter)
    }

    private fun setupTextWatchers() = with(binding) {
        edtEmail.doAfterTextChanged { text ->
            viewModel.onEmailChanged(text.toString().trim())
        }

        edtPassword.doAfterTextChanged { text ->
            viewModel.onPasswordChanged(text.toString())
        }
    }

    private fun observeViewModel() = with(binding) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoginEnabled.collect { isEnabled ->
                    btnEntrance.isEnabled = isEnabled
                    btnEntrance.alpha = if (isEnabled) 1.0f else 0.5f
                }
            }
        }
    }

    private fun setupButtons() = with(binding) {
        btnEntrance.setOnClickListener {
            viewModel.login()
            findNavController().navigate(R.id.mainFragment)
        }

        imLogoVK.setOnClickListener { openBrowser(Constants.VK_URL) }
        imLogoOK.setOnClickListener { openBrowser(Constants.OK_URL) }

        tvRegistration.isEnabled = false
        tvForgotPassword.isEnabled = false
        tvRegistration.alpha = 0.5f
        tvForgotPassword.alpha = 0.5f
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}