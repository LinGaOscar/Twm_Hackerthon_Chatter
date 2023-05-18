import os
os.environ["OPENAI_API_KEY"] ="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"

from langchain.llms import OpenAI

#llm = OpenAI(temperature=0.9)
llm = OpenAI(model_name="text-davinci-003",max_tokens=1024)
text = "What would be a good company name for a company that makes colorful socks?"
print(llm(text))