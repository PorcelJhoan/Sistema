import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(value:any,arg:any):any{
    const resultPosts=[];
    for(const post of value){
      if(post.nombre.toLowerCase().indexOf(arg.toLowerCase()) > -1 ){
        console.log("value : ",post,"\n arg : ",arg);
        resultPosts.push(post);
      };
   };
   return resultPosts;
  }
}
