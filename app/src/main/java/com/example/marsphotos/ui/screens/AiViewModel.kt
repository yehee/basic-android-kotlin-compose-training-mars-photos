package com.example.marsphotos.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.launch

@OptIn(BetaOpenAI::class)
class AiViewModel: ViewModel() {
    var gptQuery: String = "What time is it now?"
        private set
    var gptResponse: String = ""
        private set

    init {
        getGptResponse()
    }

    private fun getGptResponse() {
        viewModelScope.launch {
            val openAI = OpenAI("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")

            try {
                val chatCompletionRequest = ChatCompletionRequest(
                    model = ModelId("gpt-3.5-turbo"),
                    messages = listOf(
                        ChatMessage(
                            role = ChatRole.Assistant,
                            content = gptQuery
                        )
                    )
                )

                val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)

                val response = completion.choices.first().message?.content

                gptResponse = response ?: ""
            } catch (e: Exception) {
                // If you are using the Open AI API trial sometimes
                // cause timeout expection
                gptResponse = "ERROR: ${e.cause?.message ?: ""}"
            }
        }
    }
}