import os

os.environ["OPENAI_API_KEY"] = "sk--XLAZ7MP4oIrQQJtDTzLyT3BlbkFJB9Wgr30is9riq5GyPEgx"
os.environ["SERPAPI_API_KEY"] = "6--2858414e31145a842335cddff8a15e11b002f0779bd10ae9aa0a16abaa7fec5"

from langchain.agents import initialize_agent, Tool
from langchain.agents import AgentType
from langchain.llms import OpenAI
from langchain import LLMMathChain, SerpAPIWrapper
from langchain.chains import ConversationChain
from langchain.memory import ConversationBufferMemory
from langchain import PromptTemplate
from langchain.chains import LLMChain
from langchain.chains import SimpleSequentialChain

llm = OpenAI(temperature=0.9, model_name="text-davinci-003")

# 初始化搜索链和计算链
search = SerpAPIWrapper()
llm_math_chain = LLMMathChain(llm=OpenAI(temperature=0), verbose=True)
con_chain = ConversationChain(llm=OpenAI(temperature=0.5), verbose=True, memory=ConversationBufferMemory())

prompt = PromptTemplate(
    input_variables=["value"],
    template="""你現在是一個專業的顧問,用100字解釋一下{value}對於社會的影響""",
)
chain = LLMChain(llm=llm, prompt=prompt)

second_prompt = PromptTemplate(
    input_variables=["value"],
    template="""你現在是一個幽默的脫口秀演員,請以中文100字對觀眾用詼諧的方式說明{value}""",
)
second_chain = LLMChain(llm=llm, prompt=second_prompt)
overall_chain = SimpleSequentialChain(chains=[chain, second_chain], verbose=True)

# 创建一个功能列表，指明这个 agent 里面都有哪些可用工具，agent 执行过程可以看必知概念里的 Agent 那张图
tools = [
    Tool(
        name="Search",
        func=search.run,
        description="useful for when you need to answer questions about current events"
    ),
    Tool(
        name="Calculator",
        func=llm_math_chain.run,
        description="useful for when you need to answer questions about math"
    ),
    Tool(
        name="Interpreter",
        func=overall_chain.run,
        description="useful when you need to answer a question at final answer"
    )
]

# 初始化 agent max_iterations是最多思考次數
agent = initialize_agent(tools, llm, agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,max_iterations=5)
# agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION
# agent=AgentType.CHAT_ZERO_SHOT_REACT_DESCRIPTION

# 执行 agent
text = "這是一段對話情境，請代替MAX回應Cindy /n Max : 你呢? 平時有喜歡的做的事情嗎? /n Cindy: 平時就下班 偶爾運個小動 回家看一下書然後放空睡覺 /n Cindy: 周未跟朋友約去咖啡廳或是到處走走 /n Cindy:阿我喜歡吃東西 /n Cindy:我也喜歡看電影 就一些很平常的事情耶其實 /n Max: 我也是啦 不用那麼拘束"
# text= "最近有甚麼新的情侶活動?"
#text= "多照太陽"
# text = "目前最新的N卡代號是甚麼?大概多少台幣?"
'''I need to find this information
Action: Search
Action Input: Latest Nvidia graphics card?[0m
Observation: ?[36;1m?[1;3mThe ultimate graphics card for gamers and creators, powered by Ampere - NVIDIA's 2nd gen RTX architecture.?[0m
Thought:?[32;1m?[1;3m I now need to know the pricing of this card
Action: Search
Action Input: Price of NVIDIA GeForce RTX 3090?[0m
Observation: ?[36;1m?[1;3mRTX 3090 price Featuring 24GB of GDDR6X memory that runs at 19.5 Gbps with a boost clock of 1755MHz and 10496 CUDA cores, ther
e's little surprise that this VR-ready and Ray Tracing-beast of a graphics card launched with a staggering MSRP of $1,499 and RRP of £1,399.?[0m
Thought:?[32;1m?[1;3m I now know the final answer
Final Answer: NVIDIA GeForce RTX 3090的代號為RTX 3090，建議售價為美金$1,499，英鎊£1,399。'''


#out = agent.run(text)
print( agent.run(text))