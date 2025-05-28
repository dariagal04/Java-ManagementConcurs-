package src.mpp2024.dto;

import src.mpp2024.domain.Inscriere;
import src.mpp2024.domain.InscriereProba;
import src.mpp2024.domain.Participant;
import src.mpp2024.domain.PersoanaOficiu;

public class DTOUtils {

    public static PersoanaOficiuDTO getDTO(PersoanaOficiu persoanaOficiu) {
        return new PersoanaOficiuDTO(
                persoanaOficiu.getId(),
                persoanaOficiu.getUsername(),
                persoanaOficiu.getPassword(),
                persoanaOficiu.getLocatie_oficiu()
        );
    }

    public static PersoanaOficiu getFromDto(PersoanaOficiuDTO persoanaOficiuDTO) {
        PersoanaOficiu persoanaOficiu = new PersoanaOficiu(persoanaOficiuDTO.getId(),persoanaOficiuDTO.getUsername(),persoanaOficiuDTO.getPassword(),persoanaOficiuDTO.getLocatie_oficiu());
        return persoanaOficiu;
    }

    public static ParticipantDTO getDTO(Participant participant) {
        return new ParticipantDTO(
                participant.getId(),
                participant.getNume(),
                participant.getVarsta(),
                participant.getCnp(),
                participant.getIdPersoanaOficiu()
        );
    }

    public static Participant getFromDTO(ParticipantDTO dto) {
        Participant participant=new Participant(dto.getId(),dto.getNume(),dto.getVarsta(),dto.getCnp(),dto.getIdPersoanaOficiu());
        participant.setId(dto.getId());

        return participant;
    }

    public static InscriereProbaDTO getDTO(Inscriere inscriereProba) {
        return new InscriereProbaDTO(
                inscriereProba.getId(),
                inscriereProba.getIdParticipant(),
                inscriereProba.getIdProba(),
                inscriereProba.getIdCategorie()
        );
    }

    public static Inscriere getFromDTO(InscriereProbaDTO dto) {
        Inscriere inscriere =  new Inscriere(dto.getIdParticipant(),dto.getIdProba(),dto.getIdCategorie());
        inscriere.setId(dto.getId());
        return inscriere;
    }

    public static ParticipantDTO[] getDto(Participant[] participant) {
        ParticipantDTO[] dtos=new ParticipantDTO[participant.length];
        for(int i=0;i<participant.length;i++) {
            dtos[i]=getDTO(participant[i]);
        }
        return dtos;
    }

    public static Participant[] getFromDto(ParticipantDTO[] dto) {
        Participant[] participant=new Participant[dto.length];
        for(int i=0;i<participant.length;i++) {
            participant[i]=getFromDTO(dto[i]);
        }
        return participant;
    }

    public static InscriereProbaDTO[] getDto(Inscriere[] inscriere) {
        InscriereProbaDTO[] dtos=new InscriereProbaDTO[inscriere.length];
        for(int i=0;i<inscriere.length;i++) {
            dtos[i]=getDTO(inscriere[i]);
        }
        return dtos;
    }

    public static Inscriere[] getFromDto(InscriereProbaDTO[] dto) {
        Inscriere[] inscriere=new Inscriere[dto.length];
        for(int i=0;i<dto.length;i++) {
            inscriere[i]=getFromDTO(dto[i]);
        }
        return inscriere;
    }

}
