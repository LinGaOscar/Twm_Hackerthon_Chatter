package com.hackerthon.leonardo.controllers.restful;

import com.hackerthon.leonardo.services.LangchainService;
import com.hackerthon.leonardo.services.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/langchain")
public class LangchainController {

    private LangchainService langchainService;
    private UsageService usageService;

    @Autowired
    public LangchainController(LangchainService langchainService, UsageService usageService) {
        this.langchainService = langchainService;
        this.usageService = usageService;
    }


    @GetMapping("/healthCheck")
    public Map<String, Object> healthCheck() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> healthResultMap = langchainService.healthCheck();

        resultMap.put("result", healthResultMap);
        return resultMap;
    }

    @PostMapping("/makeIntroduce")
    public Map<String, Object> makeIntroduce(@RequestBody Map<String, Object> data) {
        Map<String, Object> resultMap = new HashMap<>();
        String UID = (String) data.get("UID");
        if (UID == null || UID.isEmpty()) {
            resultMap.put("err", "lost UID");
            return resultMap;
        }
        String apiName = "makeIntroduce";
        Map<String, Object> introduceMap = langchainService.makeIntroduce(data);
        data.put("response", introduceMap.get("response"));
        usageService.addUsage(apiName, data);

        resultMap.put("result", introduceMap);
        return resultMap;

        /*
         "UID":"g5CqU20Iv5aICKvAcLuTBtgsRqW2",
         "message":"Max,27歲,180公分,摩羯座,O型,男性,異性戀,個性活力充沛、開朗友善、積極向上,興趣戶外活動，爬山、露營、徒步旅行。喜歡小動物、狗、貓,自由職業者，工作攝影和網路營銷,相信只有在追求自己喜愛的事物的時候，才能真正享受生命的美好。"

         test data
         Max,27歲,180公分,摩羯座,O型,男性,異性戀,活力充沛、開朗友善、積極向上,喜歡戶外活動、爬山、露營、徒步旅行、小動物、狗、貓,自由職業者,從事攝影和網路營銷方面的工作,最喜歡的一句話是相信只有在追求自己喜愛的事物的時候，才能真正享受生命的美好。
         Emily,25歲,165公分,巨蟹座,A型,女性,異性戀,溫柔體貼、善解人意、充滿創造力,喜歡烹飪、插花和閱讀、動物互動、貓,設計師，從事室內設計和平面設計,最喜歡的一句話是用創意和敏銳的觀察力來豐富生活。
         Ryan,30歲,190公分,獅子座,B型,男性,異性戀,自信大方、熱情開朗、喜歡冒險挑戰,喜歡運動、籃球、足球、音樂、唱歌,音樂老師,最喜歡的一句話是熱愛音樂並分享音樂。
         Sophia,28歲,170公分,天秤座,AB型,女性,異性戀,溫和善良、理性冷靜、具有創造力,喜歡寫作、旅行、參觀藝術展覽、動物、觀察鳥類,記者,最喜歡的一句話是透過文字將觀察和感受傳達給他人。
         */
    }

    @PostMapping("/conversationHint")
    public Map<String, Object> conversationHint(@RequestBody Map<String, Object> data) {
        Map<String, Object> resultMap = new HashMap<>();
        String UID = (String) data.get("UID");
        if (UID == null || UID.isEmpty()) {
            resultMap.put("err", "lost UID");
            return resultMap;
        }
        String apiName = "conversationHint";
        Map<String, Object> hintMap = langchainService.conversationHint(data);
        data.put("response", hintMap.get("response"));
        usageService.addUsage(apiName, data);

        resultMap.put("result", hintMap);
        return resultMap;

        /*
         "UID":"g5CqU20Iv5aICKvAcLuTBtgsRqW2",
         "target":"Cindy",
         "message":"Max : 你呢? 平時有喜歡的做的事情嗎?,Cindy: 平時就下班 偶爾運個小動 回家看一下書然後放空睡覺,Cindy: 周未跟朋友約去咖啡廳或是到處走走,Cindy:阿我喜歡吃東西,Cindy:我也喜歡看電影 就一些很平常的事情耶其實,Max: 我也是啦 不用那麼拘束"

         test data
         Max: 你平常都喜歡做什麼事呢？,Emily: 下班後喜歡運動、看書、放鬆睡覺。,Emily: 假日會和朋友去咖啡廳或外出走走。Emily: 我超愛吃東西的。,Emily: 也喜歡看電影，其實就是些普通的興趣。,Max: 我也是啦，不用那麼拘束。
         Ryan: 平常有什麼喜歡做的事嗎？,Sophia: 我喜歡運動、旅行和參觀藝術展。,Sophia: 對動物也有濃厚的興趣，特別是觀察鳥類。,Sophia: 也很喜歡品味美食。,Sophia: 你呢？有什麼興趣嗎？,Ryan: 我喜歡運動，尤其是籃球和足球。,Ryan: 也愛音樂和唱歌，嗓音還不錯。
         */
    }

    @PostMapping("/personalGuide")
    public Map<String, Object> personalGuide(@RequestBody Map<String, Object> data) {
        Map<String, Object> resultMap = new HashMap<>();
        String UID = (String) data.get("UID");
        if (UID == null || UID.isEmpty()) {
            resultMap.put("err", "lost UID");
            return resultMap;
        }
        String apiName = "personalGuide";
        Map<String, Object> guideMap = langchainService.makeIntroduce(data);
        data.put("response", guideMap.get("response"));
        usageService.addUsage(apiName, data);

        resultMap.put("result", guideMap);
        return resultMap;
    }
}
