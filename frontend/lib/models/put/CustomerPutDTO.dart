import 'UserPutDTO.dart';

class CustomerPutDTO {
  final UserPutDTO? userPutDTO;

  CustomerPutDTO({
    this.userPutDTO,
  });

  factory CustomerPutDTO.fromJson(Map<String, dynamic> json) {
    return CustomerPutDTO(
      userPutDTO: json['userPutDTO'] != null
          ? UserPutDTO.fromJson(json['userPutDTO'])
          : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (userPutDTO != null) 'userPutDTO': userPutDTO!.toJson(),
    };
  }
}