import os

os.environ["OPENAI_API_KEY"] = "sk--QvTPCRd6XraDNcWe7zXHT3BlbkFJ0RzU3K8QaLp4oZ3qwyhe"
os.environ["SERPAPI_API_KEY"] = "6-2858414e31145a842335cddff8a15e11b002f0779bd10ae9aa0a16abaa7fec5"

from langchain.agents import initialize_agent, Tool
from langchain.agents import AgentType
from langchain.llms import OpenAI
from langchain import LLMMathChain, SerpAPIWrapper
from langchain import PromptTemplate
from langchain.chains import LLMChain
from langchain.chains import SimpleSequentialChain
from langchain.agent import load_tools

# build LLM chain
llm = OpenAI(temperature=0.9, model_name="text-davinci-003")

prompt = PromptTemplate(
    input_variables=["query"],
    template="{query}"
)

search = SerpAPIWrapper()
llm_math_chain = LLMMathChain(llm=OpenAI(temperature=0), verbose=True)
llm_chain = LLMChain(llm=llm, prompt=prompt, verbose=True)

# initialize the LLM tool
tools = [
    Tool(
        name="Language Model",
        func=llm_chain.run,
        description="use this tool for general purpose queries and logic"
    ),
    Tool(
        name="Search Model",
        func=search.run,
        description="useful for when you need to answer questions about current events"
    ),
    Tool(
        name="Calculator Model",
        func=llm_math_chain.run,
        description="useful for when you need to answer questions about math"
    ),
]

# 初始化 agent max_iterations是最多思考次數
agent = initialize_agent(tools, llm, agent="zero-shot-react-description", max_iterations=5, verbose=True)
# agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION
# agent=AgentType.CHAT_ZERO_SHOT_REACT_DESCRIPTION

# 执行 agent
# text = "這是一段對話情境，請代替MAX回應Cindy /n Max : 你呢? 平時有喜歡的做的事情嗎? /n Cindy: 平時就下班 偶爾運個小動 回家看一下書然後放空睡覺 /n Cindy: 周未跟朋友約去咖啡廳或是到處走走 /n Cindy:阿我喜歡吃東西 /n Cindy:我也喜歡看電影 就一些很平常的事情耶其實 /n Max: 我也是啦 不用那麼拘束"
# text = "2的三次方再乘以2是多少?"
# text = "台灣首都在哪?"
text = "一個籃子有八個蘋果,我又從旁邊帶來兩個蘋果跟四個橘子放進去之後,請問現在有多少個橘子?請解釋。"


# out = agent.run(text)
print(agent.run(text))
