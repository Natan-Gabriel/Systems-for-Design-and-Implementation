import {Injectable, Pipe, PipeTransform} from '@angular/core';
import { orderBy } from 'lodash';

@Pipe({ name: 'rentalSort' })
@Injectable()
export class RentalSortPipe implements PipeTransform {

  /*transform(value: any[], order = '', column: string = ''): any[] {
    if (!value || order === '' || !order) { return value; } // no array
    if (value.length <= 1) { return value; } // array with only one item
    if (!column || column === '') {
      if(order==='asc'){return value.sort()}
      else{return value.sort().reverse();}
    } // sort 1d array
    return orderBy(value, [column], [order]);
  }*/
  transform(items: any[], field : string, value : string): any[] {
    if (!items) return [];
    if (!value || value.length == 0) return items;
    return items.filter(it =>
      it[field].toLowerCase().indexOf(value.toLowerCase()) !=-1);
  }
}
