# WorkMachine

WorkMachine allows you to run arbitrary workflows which might need
human intervention. For example, take image transcription. It can be
processed using an OCR engine but that doesn't assure us that the
quality will be good so we can send it to a human to edit. After that
we can use that human output to train the OCR algorithm and also get
the results of the transcribed image.

The engine is generic enough to define arbitrary workflows which might
or might not need human workers in the mix. The worker infrastructure
is up to you as you will likely pass the the worker id when assigning
work. This allows you to utilize existing User schemas for use with
the engine.


## Usage

Since the app is still under heavy development currently the only way
to run the app is in development mode.

    lein run

## Documentation

Check out the wiki for more information.

     https://github.com/abhiyerra/workmachine/wiki

## License

Copyright (C) 2012  Kesava Abhinav Yerra

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
