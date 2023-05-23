import os

os.environ["OPENAI_API_KEY"] = "sk--XLAZ7MP4oIrQQJtDTzLyT3BlbkFJB9Wgr30is9riq5GyPEgx"

from langchain.llms import OpenAI
from langchain import PromptTemplate
from langchain.chains import LLMChain
from langchain.chains import SimpleSequentialChain

llm = OpenAI(temperature=0.9, model_name="text-davinci-003")

prompt = PromptTemplate(
    input_variables=["value"],
    template="""你現在是一個專業的顧問,用100字解釋一下{value}對於社會的影響""",
)
# print(llm(prompt.format(value="央行升息")))
'''央行升息對經濟的影響可以從購買力的角度來看。央行升息意味著政府把利率提高，這將使貨幣購買力下降，因此可以達到減少通膨的目的。因為支付更多的利息，人們將減
少對物品和服務的消費，從而降低了經濟活動水準。央行升息也將會導致金融市場
'''
chain = LLMChain(llm=llm, prompt=prompt)
# print(chain.run("政治貪汙"))
'''政治貪汙會對社會造成許多負面影響，比如可能會造成經濟浪費、政府倒閉、削減服務水平、違反人權、腐敗行政延宕等。此外，長期的政治貪汙會導致政治穩定受到損害，
可能會出現社會動盪和暴力抗爭等。同時，政治貪汙也會減少社會上對
'''

second_prompt = PromptTemplate(
    input_variables=["value"],
    template="""你現在是一個幽默的脫口秀演員,請以100字對觀眾用詼諧的方式說明{value}""",
)
second_chain = LLMChain(llm=llm, prompt=second_prompt)

overall_chain = SimpleSequentialChain(chains=[chain, second_chain], verbose=True)
print(overall_chain.run("盜採砂石"))
'''盜採砂石對於社會造成了嚴重的影響，尤其是在環境保護方面。盜採砂石往往導致水源污染，地下水位降低，地表水漲起，部分地區導致地形變化，對環境影響嚴重。此外，
盜採砂石還會導致農業土地被汙染，野生動植物死亡，尤其是緊鄰採礦區的居民會受到更大的影響。

雖然“盜採砂石”這個詞很慘，但是別擔心！現在政府已經開始采取有效措施來管理盜採砂石活動，為緩解社會的負擔而努力。今天，讓我們一起呼籲大家尊重自然，為我們
的環境盡一份心力！
'''
