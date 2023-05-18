import os
os.environ["OPENAI_API_KEY"] ="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"

from langchain.llms import OpenAI
from langchain.prompts import PromptTemplate
from langchain.chains import LLMChain

llm = OpenAI(temperature=0.9)

prompt = PromptTemplate(
    input_variables=["product"],
    template="What is a good name for a company that makes {product}?",
)

chain = LLMChain(llm=llm, prompt=prompt)

print(chain.run("colorful socks"))