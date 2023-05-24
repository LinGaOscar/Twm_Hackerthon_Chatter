import os

os.environ["OPENAI_API_KEY"] = "sk--QvTPCRd6XraDNcWe7zXHT3BlbkFJ0RzU3K8QaLp4oZ3qwyhe"

from langchain.llms import OpenAI
from langchain.chains import ConversationChain
from langchain.memory import ConversationBufferMemory

from langchain.prompts.prompt import PromptTemplate
from langchain.chains import LLMChain
from langchain.chains import SimpleSequentialChain
from langchain.chains import SequentialChain

# openAI
llm = OpenAI(temperature=0.9, model_name="text-davinci-003")
conversation = ConversationChain(llm=llm, verbose=True, memory=ConversationBufferMemory())

# make_introduce
trams_prompt = PromptTemplate(
    input_variables=["value"],
    template=
    """
    Please convert the text to English
    '''{value}'''
    """,
)
trams_chain = LLMChain(llm=llm, prompt=trams_prompt, verbose=True)

prompt1 = PromptTemplate(
    input_variables=["value"],
    template=
    """
    Write a self-introduction for a dating app focusing on your blood type, gender, zodiac sign, and interests.
    Your self-introduction should be at least 100 words long.
    '''{value}'''
    """,
)
chain1 = LLMChain(llm=llm, prompt=prompt1, verbose=True)

prompt2 = PromptTemplate(
    input_variables=["value"],
    template=
    """
    Introduce yourself in your own words within 50 words based on the provided information.
    Do not include basic information such as name, age, and gender.
    Discuss your attitude towards life and the importance of pursuing your passions from a first-person perspective.
    '''{value}'''
    """,
)
chain2 = LLMChain(llm=llm, prompt=prompt2, verbose=True)

chinese_reply_prompt = PromptTemplate(
    input_variables=["value"],
    template=
    """
    Please translate the text into Traditional Chinese.
    '''{value}'''
    """,
)
chinese_reply_chain = LLMChain(llm=llm, prompt=chinese_reply_prompt, verbose=True)

introduce_chain = SimpleSequentialChain(chains=[trams_chain, chain1, chain2, chinese_reply_chain], verbose=True)

# conversation_hint

conversation_hint_prompt = PromptTemplate(
    input_variables=["target", "value"],
    template=
    """
    Simulate {target}'s response in the following conversation.
    Add some details to enhance the conversation.
    Keep the answers concise.
    '''{value}'''
    """,
)
conversation_hint_chain = LLMChain(llm=llm, prompt=conversation_hint_prompt, verbose=True, output_key="result")

# fastapi

from fastapi import FastAPI
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware

# start cmd: uvicorn main2:app --reload
# http://127.0.0.1:8000/docs

app = FastAPI()  # 建立一個 Fast API application
# 設定跨域請求中間件
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:8080"],  # 允許的源網域
    allow_credentials=True,
    allow_methods=["*"],  # 允許的HTTP方法，這裡設為全部允許
    allow_headers=["*"]  # 允許的HTTP標頭，這裡設為全部允許
)


@app.get("/health_heck")  # 指定 api 路徑 (get方法)
def read_root():
    return {"response": "langchain service online"}


@app.get("/conversation")
def conversation(q: str):
    output = conversation.predict(input=q)
    return {"response": output}


class MyObject(BaseModel):
    data: dict


@app.post("/make_introduce")
def make_introduce(obj: MyObject):
    message = obj.data.get('message')
    output = introduce_chain.run(message)
    return {"response": output}


'''
"message":"Max,27歲,180公分,摩羯座,O型,男性,異性戀,個性活力充沛、開朗友善、積極向上,興趣戶外活動，爬山、露營、徒步旅行。喜歡小動物、狗、貓,自由職業者，工作攝影和網路營銷,相信只有在追求自己喜愛的事物的時候，才能真正享受生命的美好。"
'''


@app.post("/conversation_hint")
def make_introduce(obj: MyObject):
    target = obj.data.get('target')
    message = obj.data.get('message')

    output = conversation_hint_chain.run(target=target, value=message)
    return {"response": output}


'''
"target":"max",
"message":"Max : 你呢? 平時有喜歡的做的事情嗎?,Cindy: 平時就下班 偶爾運個小動 回家看一下書然後放空睡覺,Cindy: 周未跟朋友約去咖啡廳或是到處走走,Cindy:阿我喜歡吃東西,Cindy:我也喜歡看電影 就一些很平常的事情耶其實,Max: 我也是啦 不用那麼拘束"
'''
