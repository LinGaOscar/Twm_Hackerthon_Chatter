import os
os.environ["OPENAI_API_KEY"] ="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"

from langchain.llms import OpenAI
from langchain.chains import ConversationChain
from langchain.memory import ConversationBufferMemory
from langchain.prompts.prompt import PromptTemplate

from typing import Optional
from fastapi import FastAPI
#uvicorn main:app --reload
#http://127.0.0.1:8000/docs

from pydantic import BaseModel

#openAI
llm = OpenAI(temperature=0.9)
conversation = ConversationChain(llm=llm, verbose=True,memory=ConversationBufferMemory())

#fastapi
app = FastAPI() # 建立一個 Fast API application
@app.get("/HealthCheck") # 指定 api 路徑 (get方法)
def read_root():
    return {"response": "online"}

@app.get("/text")
def read_text(q: str):
    output =  conversation.predict(input=q)
    return {"response": output}


class MyObject(BaseModel):
    data: dict

@app.post("/process")
def process_object(obj: MyObject):
    question = obj.data.get('question')
    message = obj.data.get('message')
    output =  conversation.predict(input=question+message)
    return {"response": output}

'''
{
 "data": {
    "question":"這是一段對話情境，請推薦MAX要怎麼回應Cindy /n",
    "message": "Max : 你呢? 平時有喜歡的做的事情嗎? /n Cindy: 平時就下班 偶爾運個小動 回家看一下書然後放空睡覺 /n Cindy: 周未跟朋友約去咖啡廳或是到處走走 /n Cindy:阿我喜歡吃東西 /n Cindy:我也喜歡看電影 就一些很平常的事情耶其實 /n Max: 我也是啦 不用那麼拘束"
 }
}
'''