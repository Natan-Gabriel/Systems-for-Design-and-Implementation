import {Injectable, Pipe, PipeTransform} from '@angular/core';
import {Movie} from "./shared/movie.model";


@Pipe({ name: 'movieFilter' })
@Injectable()
export class MovieFilterPipe implements PipeTransform {
  /*transform(allHeroes: Movie[]) {
    console.log(1);
    return allHeroes;
  }*/
  /*transform(items: any[], callback: (item: any) => boolean): any {
    if (!items || !callback) {
      return items;
    }
    return items.filter(item => callback(item));
  }*/
  transform(items: any[], field : string, value : string): any[] {
    if (!items) return [];
    if (!value || value.length == 0) return items;
    return items.filter(it =>
      it[field].toLowerCase().indexOf(value.toLowerCase()) !=-1);
  }

}
