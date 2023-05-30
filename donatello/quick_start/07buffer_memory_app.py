import os
os.environ["OPENAI_API_KEY"] ="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"

from langchain.llms import OpenAI
from langchain.chains import ConversationChain
from langchain.memory import ConversationBufferMemory

llm = OpenAI(temperature=0)
conversation = ConversationChain(llm=llm, verbose=True,memory=ConversationBufferMemory())

while True:
    var = input("Enter something: ")
    if var == 'end':
     break
    output = conversation.predict(input=var)
    print(output)

