import os

os.environ["OPENAI_API_KEY"] = "sk--QvTPCRd6XraDNcWe7zXHT3BlbkFJ0RzU3K8QaLp4oZ3qwyhe"

from langchain.llms import OpenAI
from langchain.chains import ConversationChain
from langchain.memory import ConversationBufferMemory
from gtts import gTTS

# openAI
llm = OpenAI(temperature=0.9, model_name="text-davinci-003")
conversation = ConversationChain(llm=llm, verbose=True, memory=ConversationBufferMemory())



while True:
    user_input = input("請輸入文字：")

    if user_input == "quit":
        print("程式結束")
        break


    # 在這裡可以根據使用者輸入的文字進行相應的處理
    #print("你輸入的文字是：", user_input)
    conversation_output = conversation.predict(input=user_input)
    print(conversation_output)
    #audio = gTTS(text=conversation_output, lang="en", slow=False)
    audio = gTTS(text=conversation_output, lang="zh", slow=False)
    audio.save("response.mp3")
    os.system("start response.mp3")


