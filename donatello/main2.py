import os

os.environ["OPENAI_API_KEY"] = "sk--XLAZ7MP4oIrQQJtDTzLyT3BlbkFJB9Wgr30is9riq5GyPEgx"

from langchain.llms import OpenAI
from langchain.chains import ConversationChain
from langchain.memory import ConversationBufferMemory

from langchain.prompts.prompt import PromptTemplate
from langchain.chains import LLMChain
from langchain.chains import SimpleSequentialChain

from typing import Optional
from fastapi import FastAPI
# start cmd: uvicorn main:app --reload
# http://127.0.0.1:8000/docs

from pydantic import BaseModel

# openAI
llm = OpenAI(temperature=0.9, model_name="text-davinci-003")
conversation = ConversationChain(llm=llm, verbose=True, memory=ConversationBufferMemory())

prompt = PromptTemplate(
    input_variables=["value"],
    template="""你現在是一個專業的顧問,用100字解釋一下{value}對於社會的影響""",
)
chain = LLMChain(llm=llm, prompt=prompt)

second_prompt = PromptTemplate(
    input_variables=["value"],
    template="""你現在是一個幽默的脫口秀演員,請以100字對觀眾用詼諧的方式說明{value}""",
)
second_chain = LLMChain(llm=llm, prompt=second_prompt)

overall_chain = SimpleSequentialChain(chains=[chain, second_chain], verbose=True)
# output = overall_chain.run("盜採砂石"))

# fastapi
app = FastAPI()  # 建立一個 Fast API application


@app.get("/HealthCheck")  # 指定 api 路徑 (get方法)
def read_root():
    return {"response": "online"}


@app.get("/text")
def conversation(q: str):
    output = conversation.predict(input=q)
    return {"response": output}


class MyObject(BaseModel):
    data: dict


@app.post("/process")
def make_introduce(obj: MyObject):
    question = obj.data.get('question')
    message = obj.data.get('message')
    output = conversation.predict(input=question + message)
    return {"response": output}