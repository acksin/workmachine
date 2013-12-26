package main

import (
	"encoding/csv"
	"fmt"
	. "github.com/abhiyerra/workmachine/app"
	"os"
)

type ImageTagging struct {
	ImageUrl             InputField  `work_desc:"Use this information for the data below." work_id:"image_url" work_type:"image"`
	Tags                 OutputField `work_desc:"List all the relavent tags for this image." work_id:"tags"`
	TextInImage          OutputField `work_desc:"Put any text that appears on the image here. One line per block of text." work_id:"text_in_image"`
	IsCorrectOrientation OutputField `work_desc:"Is the image in the correct orientation?" work_id:"is_correct_orientation"`
}

func main() {
	results_file, err := os.Create("results.csv")
	if err != nil {
		panic(err)
	}
	defer results_file.Close()

	writer := csv.NewWriter(results_file)

	image_tasks := Task{
		Title:       "Tag the appropriate images",
		Description: "Look at the image and fill out the appropriate fields. We want to be able to tag all the images correctly. Fill out any appropriate tag that you see.",
		Write: func(j *Job) {
			fmt.Printf("%v\n", j)

			var output []string

			for _, i := range j.InputFields {
				output = append(output, i.Value)
				fmt.Println(i.Value)
			}

			for _, i := range j.OutputFields {
				output = append(output, i.Value)
				fmt.Println(i.Value)
			}

			if err := writer.Write(output); err != nil {
				panic(err)
			}
			writer.Flush()
		},
		Tasks: []ImageTagging{
			ImageTagging{
				ImageUrl: "http://www.flickr.com/photos/britishlibrary/11115401504",
			},
			ImageTagging{
				ImageUrl: "wwwphotos/britishlibrary/11115401504",
			},
		},
	}

	serve := HtmlServe{}
	go Serve()

	var backend Assigner = serve
	NewBatch(image_tasks).Run(backend)

}