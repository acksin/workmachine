
curl -X PUT -H "Content-Type: application/json" \
    -d '{"program": "((:topic 
                        {:input [[:instructions \"topic_instruction\"]] 
                         :output [[:string \"topic\"]]}) 
                      (:research 
                        {:input [[:instructions \"research_instruction\"] 
                                 [:text \"topic\"]] 
                         :output [[:string \"info_link\"]]}) 
                      (:write 
                        {:input [[:instructions \"write_instruction\"] 
                                 [:text \"topic\"] 
                                 [:text \"info_link\"]] 
                         :output [[:text \"content\"]]}))",
         "name": "write-wiki",
         "jobs": [{"topic_instruction": "Find something about cats.",
                   "research_instruction": "Find links for topic.",
                   "write_instruction": "Write about the topic. Use link."}]}' \
     http://localhost:8080/run

